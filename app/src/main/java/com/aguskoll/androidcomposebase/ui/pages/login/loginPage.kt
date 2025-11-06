package com.aguskoll.androidcomposebase.ui.pages.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aguskoll.R
import com.aguskoll.androidcomposebase.ui.common.PasswordTextField
import com.aguskoll.androidcomposebase.ui.theme.defaultDimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(onLoginSuccess: () -> Unit) {
    val viewModel: LogInViewModel = koinViewModel()
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val event: LoginEvent? by viewModel.eventFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(event) {
        when (event) {
            is LoginEvent.LoginSuccess -> onLoginSuccess()
            is LoginEvent.LoginError -> {
                // Handle error
            }

            else -> {

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(defaultDimensions.paddingDouble),
            modifier = Modifier.padding(defaultDimensions.paddingDouble)
        ) {
            Text(stringResource(R.string.log_in_welcome))
            TextField(
                value = uiState.email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { value ->
                    viewModel.onEmailChanged(value)
                },
                label = { Text(stringResource(R.string.log_in_email_label)) },
                isError = !uiState.isEmailValid,
                supportingText = {
                    if (!uiState.isEmailValid) {
                        Text(
                            stringResource(R.string.log_in_email_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            PasswordTextField(
                value = uiState.password,
                modifier = Modifier.fillMaxWidth(),
                onChange = {
                    viewModel.onPasswordChanged(it)
                },
                isError = !uiState.isPasswordValid,
                errorText = stringResource(R.string.log_in_password_error),
            )
            Button(onClick = { viewModel.onLoginClick() }) {
                Text(stringResource(R.string.log_in_button))
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginPagePreview() {
    LoginPage({})
}

