package com.fl0w3r.model

data class RegisterModel(
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val age: Int
)