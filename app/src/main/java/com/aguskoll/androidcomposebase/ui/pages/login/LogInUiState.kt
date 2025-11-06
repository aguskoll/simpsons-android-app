package com.aguskoll.androidcomposebase.ui.pages.login

import com.aguskoll.androidcomposebase.ui.base.UiState

data class LogInUiState(
    val email: String = "",
    val emailErrorCode: String? = null,
    val password: String = "",
    val passwordErrorCode: String? = null,
    val isPasswordValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false
) : UiState

sealed interface LoginEvent {
    object LoginSuccess : LoginEvent
    data class LoginError(val error: String) : LoginEvent
}
