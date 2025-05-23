package com.thomas200593.mdm.features.introduction.onboarding.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.common.cnf_localization_language.entity.Language
import com.thomas200593.mdm.features.common.cnf_localization_language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.introduction.onboarding.domain.UCFinishOnboarding
import com.thomas200593.mdm.features.introduction.onboarding.domain.UCGetScreenData
import com.thomas200593.mdm.features.introduction.onboarding.ui.events.Events
import com.thomas200593.mdm.features.introduction.onboarding.ui.state.FormOnboardingState
import com.thomas200593.mdm.features.introduction.onboarding.ui.state.ScreenDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val ucGetScreenData: UCGetScreenData,
    private val ucFinishOnboarding: UCFinishOnboarding,
    private val repoConfLanguage: RepoConfLanguage
) : ViewModel() {
    data class UiState(val screenData: ScreenDataState = ScreenDataState.Loading)
    var uiState = MutableStateFlow(UiState()) ; private set
    var formOnboarding by mutableStateOf(FormOnboardingState())
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
    private fun handleOpenScreen() = viewModelScope.launch { ucGetScreenData.invoke()
        .onStart { formOnboarding = FormOnboardingState()
            uiState.update { it.copy(screenData = ScreenDataState.Loading) } }
        .collect { (confCommon, languages, onboardingPages) -> uiState.update { it.copy(
            screenData = ScreenDataState.Loaded(
                confCommon = confCommon, languages = languages, onboardingPages = onboardingPages
            )) } ; formOnboarding = formOnboarding.copy(listCurrentIndex = 0,
            listMaxIndex = onboardingPages.size.minus(1))
        }
    }
    private fun handleSelectLanguage(language: Language) =
        viewModelScope.launch { repoConfLanguage.set(language) }
    private fun handlePageAction(action : Events.Action) { val newIdx = when (action) {
        Events.Action.PREV -> formOnboarding.listCurrentIndex.minus(1).coerceAtLeast(0)
        Events.Action.NEXT -> formOnboarding.listCurrentIndex.plus(1).coerceAtMost(formOnboarding.listMaxIndex)
    } ; formOnboarding = formOnboarding.copy(listCurrentIndex = newIdx) }
    private fun handlePageFinish() = viewModelScope.launch { ucFinishOnboarding.invoke() }
}