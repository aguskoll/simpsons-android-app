package com.aguskoll.androidcomposebase.utils

object Validator {
    private val PASSWORD_REGEX =
        Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!]).*$")

    fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && email.contains("@") && email.contains(".com")
    }

    fun validatePassword(password: String): Boolean {
        return PASSWORD_REGEX.matches(password)
    }
}