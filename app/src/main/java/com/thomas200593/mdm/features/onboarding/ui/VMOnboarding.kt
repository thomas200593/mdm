package com.thomas200593.mdm.features.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.onboarding.domain.UCFinishOnboarding
import com.thomas200593.mdm.features.onboarding.domain.UCGetDataOnboarding
import com.thomas200593.mdm.features.onboarding.ui.events.Events
import com.thomas200593.mdm.features.onboarding.ui.state.ComponentsState
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
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvents(screenEvents: Events.Screen) = when(screenEvents) {
        is Events.Screen.OnOpen -> onOpenEvent()
    }
    fun onTopAppBarEvents(topAppBarEvents: Events.TopAppBar) = when(topAppBarEvents) {
        is Events.TopAppBar.BtnLanguage.OnSelect -> onSelectLanguageEvent(topAppBarEvents.language)
    }
    fun onBottomBarEvents(bottomAppBarEvents: Events.BottomAppBar) = when(bottomAppBarEvents) {
        is Events.BottomAppBar.BtnNavActions.PageAction -> onNavOnboardingPageEvent(bottomAppBarEvents.action)
        is Events.BottomAppBar.BtnNavActions.Finish -> onNavOnboardingFinishEvent()
    }
    private fun onOpenEvent() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {
            ucGetDataOnboarding.invoke().collect { result ->
                uiState.update { currentState ->
                    currentState.copy(
                        componentsState = ComponentsState.Loaded(
                            confCommon = result.confCommon,
                            languageList = result.languageList,
                            onboardingPages = result.onboardingPages,
                            listCurrentIndex = result.listCurrentIndex,
                            listMaxIndex = result.listMaxIndex
                        )
                    )
                }
            }
        }
    }
    private fun onSelectLanguageEvent(language: Language) = viewModelScope.launch { repoConfLanguage.set(language) }
    private fun onNavOnboardingPageEvent(navigate: Events.OnboardingButtonNav) = uiState.update { currentState ->
        (currentState.componentsState as? ComponentsState.Loaded) ?.let { state ->
            val newIdx = when(navigate) {
                Events.OnboardingButtonNav.PREV -> state.listCurrentIndex.minus(1).coerceAtLeast(0)
                Events.OnboardingButtonNav.NEXT -> state.listCurrentIndex.plus(1).coerceAtMost(state.listMaxIndex)
            }
            currentState.copy(state.copy(listCurrentIndex = newIdx))
        } ?: currentState
    }
    private fun onNavOnboardingFinishEvent() = viewModelScope.launch { ucFinishOnboarding.invoke() }
}