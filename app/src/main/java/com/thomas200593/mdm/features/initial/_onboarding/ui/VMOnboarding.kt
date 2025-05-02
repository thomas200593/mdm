package com.thomas200593.mdm.features.initial._onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.initial._onboarding.domain.UCFinishOnboarding
import com.thomas200593.mdm.features.initial._onboarding.domain.UCGetScreenData
import com.thomas200593.mdm.features.initial._onboarding.ui.events.Events
import com.thomas200593.mdm.features.initial._onboarding.ui.state.ComponentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucFinishOnboarding: UCFinishOnboarding,
    private val repoConfLanguage: RepoConfLanguage
) : ViewModel() {
    data class UiState(val componentsState: ComponentsState = ComponentsState.Loading)
    var uiState = MutableStateFlow(UiState())
        private set
    fun onScreenEvent(event: Events.Screen) = when(event) {
        is Events.Screen.Opened -> handleOpenScreen()
    }
    fun onTopBarEvent(event: Events.TopBar) = when(event) {
        is Events.TopBar.BtnLanguage.Selected -> handleSelectLanguage(event.language)
    }
    fun onBottomBarEvent(event: Events.BottomBar) = when(event) {
        is Events.BottomBar.NavButton.Page -> handlePageAction(event.action)
        is Events.BottomBar.NavButton.Finish -> handlePageFinish()
    }
    private fun handleOpenScreen() {
        uiState.update { it.copy(componentsState = ComponentsState.Loading) }
        viewModelScope.launch {
            ucGetScreenData.invoke().collect { result ->
                uiState.update { currentState -> currentState.copy(
                    componentsState = ComponentsState.Loaded(
                        confCommon = result.confCommon,
                        languages = result.languageList,
                        onboardingPages = result.onboardingPages,
                        currentIndex = result.listCurrentIndex,
                        maxIndex = result.listMaxIndex
                    )
                ) }
            }
        }
    }
    private fun handleSelectLanguage(language: Language) = viewModelScope.launch { repoConfLanguage.set(language) }
    private fun handlePageAction(action: Events.Action) = uiState.update { currentState ->
        (currentState.componentsState as? ComponentsState.Loaded) ?.let { state ->
            val newIdx = when(action) {
                Events.Action.PREV -> state.currentIndex.minus(1).coerceAtLeast(0)
                Events.Action.NEXT -> state.currentIndex.plus(1).coerceAtMost(state.maxIndex)
            }
            currentState.copy(state.copy(currentIndex = newIdx))
        } ?: currentState
    }
    private fun handlePageFinish() = viewModelScope.launch { ucFinishOnboarding.invoke() }
}