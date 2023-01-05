package com.fl0w3r.user.ui.login.state

data class TokenState(
    val isValid: Boolean,
    val token: String,
    val errorMessage: String = ""
)