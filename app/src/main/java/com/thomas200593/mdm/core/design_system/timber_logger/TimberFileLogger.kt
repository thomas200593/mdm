package com.thomas200593.mdm.core.design_system.timber_logger

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.CoroutineDispatchers
import com.thomas200593.mdm.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mdm.core.design_system.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

interface TimberFileLogger {
    fun log(priority: Int, tag: String?, message: String, throwable: Throwable? = null)
    fun flush(line: String)
    fun shareLogFile(context: Context)
}
@Singleton
class TimberFileFileLoggerImpl @Inject constructor(
    @Dispatcher(CoroutineDispatchers.IO) private val ioDispatcher : CoroutineDispatcher,
    @ApplicationContext context: Context
) : TimberFileLogger {
    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())
    private val logDir: File = File(context.filesDir, "logs").apply { mkdirs() }
    private val logFile: File = File(logDir, "app_log.log")
    private val maxSizeBytes: Long = 1 * 1024 * 1024 // 1MB
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    init {
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                this@TimberFileFileLoggerImpl.log(priority, tag, message, t)
            }
        })
        cleanupOldLogs()
    }
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        val logLine = "${dateFormat.format(Date())} | ${priorityToString(priority)}/${tag ?: "NoTag"} | $message\n"
        scope.launch {
            runCatching {
                rotateLogFileIfNeeded()
                FileWriter(logFile, true).use { writer ->
                    writer.append(logLine)
                    if (throwable != null) writer.append(" | ${throwable.stackTraceToString()}\n")
                }
            }.onFailure { Timber.tag("TimberFileFileLoggerImpl").e(it, "Failed to write log") }
        }
    }
    override fun flush(line: String) {
        scope.launch {
            rotateLogFileIfNeeded()
            logFile.appendText("${dateFormat.format(Date())} | INFO/Flush | $line\n")
        }
    }
    override fun shareLogFile(context: Context) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            logFile
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share logs via"))
    }
    private fun rotateLogFileIfNeeded() {
        if (logFile.length() >= maxSizeBytes) {
            val rotated = File(logDir, "app_log_${System.currentTimeMillis()}.log")
            logFile.copyTo(rotated, overwrite = true)
            logFile.writeText(Constants.STR_EMPTY)
        }
    }
    private fun cleanupOldLogs(keepLast: Int = 3) {
        val logs = logDir.listFiles { f -> f.name.startsWith("app_log_") }
            ?.sortedByDescending { it.lastModified() }
            ?: return
        logs.drop(keepLast).forEach { it.delete() }
    }
    private fun priorityToString(priority: Int): String = when (priority) {
        Log.VERBOSE -> "V"
        Log.DEBUG -> "D"
        Log.INFO -> "I"
        Log.WARN -> "W"
        Log.ERROR -> "E"
        Log.ASSERT -> "A"
        else -> priority.toString()
    }
}