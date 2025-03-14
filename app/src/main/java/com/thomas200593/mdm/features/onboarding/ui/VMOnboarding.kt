package com.thomas200593.mdm.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.onboarding.domain.UCFinishOnboarding
import com.thomas200593.mdm.features.onboarding.domain.UCGetDataOnboarding
import com.thomas200593.mdm.features.onboarding.entity.OnboardingScrData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val ucGetDataOnboarding: UCGetDataOnboarding,
    private val ucFinishOnboarding: UCFinishOnboarding,
    private val repoConfLanguage: RepoConfLanguage
) : ViewModel() {
    sealed interface Ui {
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Loaded(val data : OnboardingScrData) : DataState
        }
        sealed interface Events : Ui {
            data object OnOpenEvent : Events
            data class OnSelectLanguage(val language: Language) : Events
            data object OnNavPrevPage : Events
            data object OnNavNextPage : Events
            data object OnNavFinish : Events
        }
    }

    var uiState = MutableStateFlow(Ui.Data())
        private set

    fun onEvent(events : Ui.Events) {
        when (events) {
            is Ui.Events.OnOpenEvent -> onOpenEvent()
            is Ui.Events.OnSelectLanguage -> onSelectLanguage(events.language)
            is Ui.Events.OnNavPrevPage -> onNavPrevPageEvent()
            is Ui.Events.OnNavNextPage -> onNavNextPageEvent()
            is Ui.Events.OnNavFinish -> onNavFinishEvent()
        }
    }

    private fun onOpenEvent() = viewModelScope.launch {
        ucGetDataOnboarding.invoke().collect { data ->
            uiState.update {
                it.copy(dataState = Ui.DataState.Loaded(data = data.copy(listMaxIndex = data.onboardingPages.size - 1)))
            }
        }
    }
    private fun onSelectLanguage(language: Language) = viewModelScope.launch { repoConfLanguage.set(language) }
    private fun onNavPrevPageEvent() = uiState.update {
        (it.dataState as? Ui.DataState.Loaded)?.let { state ->
            val prevPage = (state.data.listCurrentIndex - 1).coerceAtLeast(0)
            it.copy(dataState = state.copy(data = state.data.copy(listCurrentIndex = prevPage)))
        } ?: it // Return unchanged if not in Loaded state
    }
    private fun onNavNextPageEvent() = uiState.update {
        (it.dataState as? Ui.DataState.Loaded)?.let { state ->
            val nextPage = (state.data.listCurrentIndex + 1).coerceAtMost(state.data.listMaxIndex)
            it.copy(dataState = state.copy(data = state.data.copy(listCurrentIndex = nextPage)))
        } ?: it // Return unchanged if not in Loaded State
    }
    private fun onNavFinishEvent() = viewModelScope.launch { ucFinishOnboarding.invoke() }
}