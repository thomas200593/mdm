package com.thomas200593.mdm.features.onboarding.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.__language.repository.RepoConfLanguage
import com.thomas200593.mdm.features.onboarding.domain.UCGetDataOnboarding
import com.thomas200593.mdm.features.onboarding.entity.OnboardingScrData
import com.thomas200593.mdm.features.onboarding.repository.RepoOnboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMOnboarding @Inject constructor(
    private val ucGetDataOnboarding: UCGetDataOnboarding,
    private val repoOnboarding: RepoOnboarding,
    private val repoConfLanguage: RepoConfLanguage
) : ViewModel() {
    sealed interface Ui {
        data class Data(val dataState: DataState = DataState.Loading) : Ui
        sealed interface DataState : Ui {
            data object Loading : DataState
            data class Success(val data : OnboardingScrData) : DataState
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
                it.copy(
                    dataState = Ui.DataState.Success(
                        data = data.copy(
                            listMaxIndex = data.onboardingPages.size - 1
                        )
                    )
                )
            }
        }
    }

    private fun onSelectLanguage(language: Language) {
        viewModelScope.launch { repoConfLanguage.set(language) }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(Locale(language.code)))
    }

    private fun onNavPrevPageEvent() = uiState.update {
        (it.dataState as? Ui.DataState.Success)?.let { state ->
            val prevPage = (state.data.listCurrentIndex - 1).coerceAtLeast(0)
            it.copy(
                dataState = state.copy(
                    data = state.data.copy(
                        listCurrentIndex = prevPage
                    )
                )
            )
        } ?: it // Return unchanged if not in Success state
    }

    private fun onNavNextPageEvent() = uiState.update {
        (it.dataState as? Ui.DataState.Success)?.let { state ->
            val nextPage = (state.data.listCurrentIndex + 1).coerceAtMost(state.data.listMaxIndex)
            it.copy(
                dataState = state.copy(
                    data = state.data.copy(
                        listCurrentIndex = nextPage
                    )
                )
            )
        } ?: it // Return unchanged if not in Success State
    }

    private fun onNavFinishEvent() = viewModelScope.launch { repoOnboarding.hide() }
}