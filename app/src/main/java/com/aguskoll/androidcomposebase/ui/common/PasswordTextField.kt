package com.aguskoll.androidcomposebase.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.aguskoll.R

@Composable
fun PasswordTextField(
    value: String,
    modifier: Modifier,
    onChange: (String)-> Unit,
    isError: Boolean,
    errorText:String,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    TextField(
        value,
        modifier = modifier,
        onValueChange =  onChange,
        label = { Text(text = stringResource( R.string.log_in_password_label)) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = if (isPasswordVisible) stringResource(R.string.log_in_show_password) else stringResource(R.string.log_in_hide_password),
                modifier = Modifier.clickable {
                    isPasswordVisible = !isPasswordVisible
                }
            )
        },
        singleLine = true,
        supportingText = {
            if (isError) {
                Text(errorText, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}