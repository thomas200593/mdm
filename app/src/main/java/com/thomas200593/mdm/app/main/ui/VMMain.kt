package com.thomas200593.mdm.app.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.app.main.domain.UCGetMainData
import com.thomas200593.mdm.app.main.ui.state.UiStateMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VMMain @Inject constructor(
    ucGetMainData: UCGetMainData
): ViewModel() {
    val uiState: StateFlow<UiStateMain> = flowOf(UiStateMain.Loading)
        .stateIn(
            scope = viewModelScope,
            initialValue = UiStateMain.Loading,
            started = SharingStarted.Eagerly
        )
}