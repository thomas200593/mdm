package com.thomas200593.mdm.features.auth.ui.state

sealed interface ResultSignIn {
    data object Idle : ResultSignIn
    data object Loading : ResultSignIn
    data class Success(val result: Int) : ResultSignIn
    data class Error(val t: Throwable) : ResultSignIn
}