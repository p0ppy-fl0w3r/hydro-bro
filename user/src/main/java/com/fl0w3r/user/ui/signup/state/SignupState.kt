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
    var usernameError: String = "",
    var firstNameError: String = "",
    var lastNameError: String = "",
    var ageError: String = "",
    var emailError: String = "",
    var passwordError: String = ""
)

data class SignupApiState(
    val status: ApiStatus,
    val message: String
)

enum class ApiStatus {
    INITIAL,
    PENDING,
    SUCCESS,
    FAILED
}