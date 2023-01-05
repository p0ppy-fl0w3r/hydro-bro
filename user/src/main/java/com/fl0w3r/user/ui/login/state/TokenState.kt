package com.fl0w3r.user.ui.login.state

data class TokenState(
    val validity: TokenValid, val token: String, val errorMessage: String = ""
)

enum class TokenValid {
    FRESH_LOGIN, AWAITING_VALIDITY, FROM_STORE, FROM_API, INVALID
}