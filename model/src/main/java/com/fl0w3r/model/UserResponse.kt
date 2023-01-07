package com.fl0w3r.model

data class UserResponse(
    val token: String,
    val username: String,
    val email: String,
    val age: Int,
    val firstName: String,
    val lastName: String
)