package com.thomas200593.mdm.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.onboarding.domain.UCFinishOnboarding
import com.thomas200593.mdm.features.onboarding.domain.UCGetDataOnboarding
import com.thomas200593.mdm.features.onboarding.entity.Onboarding
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
    data class ScrData(
        val confCommon: Common,
        val languageList: List<Language>,
        val onboardingPages: List<Onboarding>,
        val listCurrentIndex: Int,
        val listMaxIndex: Int
    )
    sealed interface ScrDataState {
        data object Loading : ScrDataState
        data class Loaded(val scrData : ScrData) : ScrDataState
    }
    data class UiState(
        val scrDataState: ScrDataState = ScrDataState.Loading
    )
    sealed interface Events {
        enum class OnboardingButtonNav { NEXT, PREV }
        data object OnOpenEvent : Events
        sealed interface TopAppBarEvents : Events {
            sealed interface BtnLanguageEvents : TopAppBarEvents {
                data class OnSelect(val language: Language) : Events
            }
        }
        sealed interface BottomAppBarEvents : Events {
            sealed interface BtnNavActions : BottomAppBarEvents {
                data class PageAction(val action: OnboardingButtonNav) : BtnNavActions
                data object Finish : BtnNavActions
            }
        }
    }
    var uiState = MutableStateFlow(UiState())
        private set
    fun onEvent(events: Events) {
        when(events) {
            is Events.OnOpenEvent -> onOpenEvent()
            is Events.TopAppBarEvents.BtnLanguageEvents.OnSelect -> onBtnLanguageSelect(events.language)
            is Events.BottomAppBarEvents.BtnNavActions.PageAction -> onPageAction(events.action)
            is Events.BottomAppBarEvents.BtnNavActions.Finish -> onNavFinishEvent()
        }
    }
    private fun onOpenEvent() = viewModelScope.launch { ucGetDataOnboarding.invoke()
        .collect { scrData -> uiState.update { it.copy(scrDataState = ScrDataState.Loaded(scrData = scrData)) } } }
    private fun onBtnLanguageSelect(language: Language) =
        viewModelScope.launch { repoConfLanguage.set(language) }
    private fun onPageAction(navigate: Events.OnboardingButtonNav) {
        uiState.update {
            (it.scrDataState as? ScrDataState.Loaded)?.let { state ->
                val newIdx = when(navigate) {
                    Events.OnboardingButtonNav.PREV -> (state.scrData.listCurrentIndex.minus(1)).coerceAtLeast(0)
                    Events.OnboardingButtonNav.NEXT -> (state.scrData.listCurrentIndex.plus(1)).coerceAtMost(state.scrData.listMaxIndex)
                }
                it.copy(state.copy(scrData = state.scrData.copy(listCurrentIndex = newIdx)))
            } ?: it
        }
    }
    private fun onNavFinishEvent() = viewModelScope.launch { ucFinishOnboarding.invoke() }
}