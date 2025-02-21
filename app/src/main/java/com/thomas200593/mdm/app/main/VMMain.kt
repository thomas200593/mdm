package com.thomas200593.mdm.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.app.main.domain.UCGetDataMain
import com.thomas200593.mdm.app.main.ui.state.UiStateMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VMMain @Inject constructor(ucGetDataMain: UCGetDataMain): ViewModel() {
    val uiState: StateFlow<UiStateMain> = ucGetDataMain.invoke().map { UiStateMain.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = UiStateMain.Loading,
            started = SharingStarted.Eagerly
        )
}