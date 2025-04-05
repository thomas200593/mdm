package com.thomas200593.mdm.features.onboarding.ui.events

import com.thomas200593.mdm.features.conf.__language.entity.Language

sealed interface Events {
    enum class OnboardingButtonNav { NEXT, PREV }
    sealed interface Screen : Events {
        data object OnOpen : Screen
    }
    sealed interface TopAppBar : Events {
        sealed interface BtnLanguage : TopAppBar {
            data class OnSelect(val language: Language) : TopAppBar
        }
    }
    sealed interface BottomAppBar : Events {
        sealed interface BtnNavActions : BottomAppBar {
            data class PageAction(val action: OnboardingButtonNav) : BtnNavActions
            data object Finish : BtnNavActions
        }
    }
}