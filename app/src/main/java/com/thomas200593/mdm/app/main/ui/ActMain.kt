package com.thomas200593.mdm.app.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thomas200593.mdm.app.main.ui.state.UiData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActMain: AppCompatActivity() {

    private val vm: VMMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiData by mutableStateOf(UiData(1))

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(flowOf(true /*TODO*/), flowOf(true /*TODO*/)) { _, _ -> }
                    .onEach { /*TODO*/ }.map { /*TODO*/ }.distinctUntilChanged().collect{ enableEdgeToEdge(/*TODO*/) }
            }
        }

        splashscreen.setKeepOnScreenCondition { vm.uiState.value.keepSplashScreenOn() }

        setContent { /*TODO*/ }
    }
}