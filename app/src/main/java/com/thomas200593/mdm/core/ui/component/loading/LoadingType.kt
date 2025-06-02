package com.thomas200593.mdm.core.ui.component.loading

sealed interface LoadingType {
    data object Dialog : LoadingType
    data object Screen : LoadingType
    data object InnerCircularProgressIndicator : LoadingType
}