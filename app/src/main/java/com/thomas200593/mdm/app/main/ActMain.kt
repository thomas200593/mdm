package com.thomas200593.mdm.app.main

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thomas200593.mdm.app.main.ui.ScrApp
import com.thomas200593.mdm.app.main.ui.state.UiData
import com.thomas200593.mdm.app.main.ui.state.UiStateMain
import com.thomas200593.mdm.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mdm.features.session.SessionManager
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.rememberStateApp
import com.thomas200593.mdm.core.design_system.timber_logger.LocalTimberFileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.di.TimberFileLoggerEntryPoint
import com.thomas200593.mdm.core.ui.common.Color
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.core.ui.component.isSystemInDarkTheme
import com.thomas200593.mdm.core.ui.component.setupSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint class ActMain : AppCompatActivity() {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var sessionManager: SessionManager
    private lateinit var timberFileLogger: TimberFileLogger
    private val vm: VMMain by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        timberFileLogger = EntryPointAccessors.fromApplication(applicationContext, TimberFileLoggerEntryPoint::class.java).timberFileLogger()
        var uiData by mutableStateOf(UiData(
            darkThemeEnabled = resources.configuration.isSystemInDarkTheme, dynamicColorEnabled = UiStateMain.Loading.dynamicColorEnabled,
            contrastAccent = UiStateMain.Loading.contrastAccent, fontSize = UiStateMain.Loading.fontSize
        ))
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(flow = isSystemInDarkTheme(), flow2 = vm.uiState) { systemDark, uiState ->
                    UiData(
                        darkThemeEnabled = uiState.darkThemeEnabled(systemDark), dynamicColorEnabled = uiState.dynamicColorEnabled,
                        contrastAccent = uiState.contrastAccent, fontSize = uiState.fontSize
                    )
                }.onEach { uiData = it }.map { it.darkThemeEnabled }.distinctUntilChanged().collect { darkTheme -> enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = android.graphics.Color.TRANSPARENT, darkScrim = android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.Light.scrimARGB, darkScrim = Color.Dark.scrimARGB,
                    ) { darkTheme }
                ) }
            }
        }
        splashscreen.setKeepOnScreenCondition { vm.uiState.value.keepSplashScreenOn() }
        setupSplashScreen(splashscreen)
        setContent {
            val appState = rememberStateApp(networkMonitor = networkMonitor, timberFileLogger = timberFileLogger, sessionManager = sessionManager)
            CompositionLocalProvider(LocalStateApp provides appState, LocalTimberFileLogger provides timberFileLogger) {
                Theme.AppTheme(
                    darkThemeEnabled = uiData.darkThemeEnabled, dynamicColorEnabled = uiData.dynamicColorEnabled,
                    contrastAccent = uiData.contrastAccent, fontSize = uiData.fontSize, content = { ScrApp() }
                )
            }
        }
    }
}