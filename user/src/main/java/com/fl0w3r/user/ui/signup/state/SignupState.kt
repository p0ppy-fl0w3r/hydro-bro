package com.fl0w3r.user.ui.signup.state

data class SignupState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val age: Int? = null,
    val email: String = "",
    val password: String = ""
)

data class SignupErrorState(
    val usernameError: String = "",
    val firstNameError: String = "",
    val lastNameError: String = "",
    val ageError: String = "",
    val emailError: String = "",
    val passwordError: String = ""
)