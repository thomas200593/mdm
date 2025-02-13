package com.thomas200593.mdm.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.app.main.domain.UCGetMainData
import com.thomas200593.mdm.app.main.ui.state.UiStateMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VMMain @Inject constructor(ucGetMainData: UCGetMainData): ViewModel() {
    val uiState: StateFlow<UiStateMain> = ucGetMainData.invoke().map { UiStateMain.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = UiStateMain.Loading,
            started = SharingStarted.Eagerly
        )
}