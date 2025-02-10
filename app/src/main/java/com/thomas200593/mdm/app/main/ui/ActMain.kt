package com.thomas200593.mdm.app.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thomas200593.mdm.app.main.ui.state.UiData
import com.thomas200593.mdm.app.main.ui.state.UiStateMain
import com.thomas200593.mdm.core.ui.common.Color
import com.thomas200593.mdm.core.ui.component.isSystemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActMain: ComponentActivity() {

    private val vm: VMMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiData by mutableStateOf(
            UiData(
                darkThemeEnabled = resources.configuration.isSystemInDarkTheme,
                dynamicColorEnabled = UiStateMain.Loading.dynamicColorEnabled
            )
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    flow = isSystemInDarkTheme(),
                    flow2 = vm.uiState
                ) { systemDark, uiState ->
                    UiData(
                        darkThemeEnabled = uiState.darkThemeEnabled(systemDark),
                        dynamicColorEnabled = uiState.dynamicColorEnabled
                    )
                }.onEach { uiData = it }.map { it.darkThemeEnabled }.distinctUntilChanged()
                    .collect{ darkTheme ->
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                lightScrim = android.graphics.Color.TRANSPARENT,
                                darkScrim = android.graphics.Color.TRANSPARENT,
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                lightScrim = Color.Light.scrimARGB,
                                darkScrim = Color.Dark.scrimARGB,
                            ) { darkTheme }
                        )
                    }
            }
        }

        splashscreen.setKeepOnScreenCondition { vm.uiState.value.keepSplashScreenOn() }

        setContent { /*TODO*/ }
    }
}