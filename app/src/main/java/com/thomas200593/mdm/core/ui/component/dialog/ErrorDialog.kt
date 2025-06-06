package com.thomas200593.mdm.core.ui.component.dialog

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.error.Error
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.PanelCard
import com.thomas200593.mdm.core.ui.component.text.TextType
import com.thomas200593.mdm.core.ui.component.text.UiText
import kotlinx.coroutines.launch

@Composable fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String = stringResource(R.string.str_error),
    message: String = stringResource(R.string.str_error),
    error: Error? = null,
    showStackTrace: Boolean = BuildConfig.DEBUG,
    btnConfirmText: String = stringResource(R.string.str_back)
) {
    val clipDataLabel = buildString { append(stringResource(R.string.app_name)); append(Constants.STR_UNDERSCORE); append(stringResource(R.string.str_error)) }
    val clipboardManager = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()
    var isExpanded by remember { mutableStateOf(false) }
    BaseAlertDialog(
        onDismissRequest = onDismissRequest,
        dialogType = DialogType.ERROR,
        icon = { Icon(Icons.Default.Close, contentDescription = null) },
        title = { UiText(title) },
        text = { LazyColumn (
            modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
            content = {
                item { UiText(text = message) }
                error?.let {
                    it.message
                        ?. takeIf { msg -> msg.isNotBlank() }
                        ?. let { msg -> item { UiText("Failure Message: ${it.emoji} $msg", modifier = Modifier.fillMaxWidth()) } }
                    it.cause ?. toString()
                        ?. takeIf { c -> c.isNotBlank() }
                        ?. let { causeStr -> item { UiText("Cause: $causeStr", modifier = Modifier.fillMaxWidth()) } }
                    if (showStackTrace) {
                        val traceText = it.stackTrace.joinToString("\n") { ste -> ste.toString() }
                        item { PanelCard(
                            modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp16),
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        UiText(
                                            text = if (isExpanded) "StackTrace (Tap to hide)" else "StackTrace (Tap to show)",
                                            type = TextType.LABEL_MD,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Row(content =  {
                                            IconButton(
                                                onClick = {
                                                    val stackTrace = AnnotatedString(traceText).text
                                                    coroutineScope.launch { clipboardManager
                                                        .setClipEntry(ClipEntry(ClipData.newPlainText(clipDataLabel, stackTrace))) }
                                                },
                                                content = { Icon(Icons.Default.ContentCopy, contentDescription = "Copy") }
                                            )
                                            IconToggleButton(
                                                checked = isExpanded,
                                                onCheckedChange = { isExpanded = !isExpanded },
                                                content = { Icon(
                                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                    contentDescription = null
                                                ) }
                                            )
                                        })
                                    }
                                )
                            },
                            content = { if (isExpanded && traceText.isNotBlank()) UiText(
                                text = traceText,
                                modifier = Modifier.fillMaxWidth()
                            ) }
                        ) }
                    }
                }
            }
        ) },
        confirmButton = { Button(onClick = onDismissRequest, content = { UiText(btnConfirmText) }) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}