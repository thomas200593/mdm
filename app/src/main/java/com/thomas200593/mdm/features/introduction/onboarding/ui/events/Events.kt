package com.thomas200593.mdm.features.introduction.onboarding.ui.events

import com.thomas200593.mdm.features.common.cnf_localization_language.entity.Language

sealed interface Events {
    enum class Action { NEXT, PREV }
    sealed interface Screen : Events {
        data object Opened : Screen
    }
    sealed interface TopBar : Events {
        sealed interface BtnLanguage : TopBar {
            data class Selected(val language: Language) : TopBar
        }
    }
    sealed interface BottomBar : Events {
        sealed interface NavButton : BottomBar {
            data class Page(val action: Action) : NavButton
            data object Finish : NavButton
        }
    }
}