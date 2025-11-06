package com.aguskoll.androidcomposebase.ui.pages.login

import androidx.lifecycle.viewModelScope
import com.aguskoll.androidcomposebase.ui.base.BaseViewModel
import com.aguskoll.androidcomposebase.utils.Validator
import com.aguskoll.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LogInUiState, LoginEvent>(LogInUiState()) {

    fun onEmailChanged(email: String) {
        updateUiState { state -> state.copy(email = email) }
        validateEmail(email)
    }

    fun onPasswordChanged(password: String) {
        updateUiState { state -> state.copy(password = password) }
        validatePassword(password)
    }

    fun onLoginClick() {
        if (!uiState.isEmailValid || !uiState.isPasswordValid) {
            pushEvent(LoginEvent.LoginError("Please check your email and password"))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { it.copy(isLoading = true) }
            val result = loginUseCase(uiState.email, uiState.password)
            result
                .onSuccess {
                    updateUiState { state -> state.copy(isLoading = false) }
                    pushEvent(LoginEvent.LoginSuccess)
                }
                .onFailure { e ->
                    updateUiState { state -> state.copy(isLoading = false) }
                    pushEvent(
                        LoginEvent.LoginError(
                            e.message ?: "Login failed. Please try again."
                        )
                    )
                }
        }
    }

    private fun validateEmail(email: String) {
        updateUiState { state -> state.copy(isEmailValid = Validator.validateEmail(email)) }
    }

    private fun validatePassword(password: String) {
        updateUiState { state -> state.copy(isPasswordValid = Validator.validatePassword(password)) }
    }
}
