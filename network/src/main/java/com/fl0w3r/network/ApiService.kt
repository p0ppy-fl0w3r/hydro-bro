package com.fl0w3r.network

import com.fl0w3r.model.UserResponse
import kotlinx.coroutines.delay

class ApiService {
    // TODO Implement api.
    suspend fun isTokenValid(token: String): Boolean {
        delay(1000)

        return token.isNotEmpty()
    }

    suspend fun getToken(username: String, password: String): UserResponse {
        if (password == "cat" && username == "Cat42069") {
            delay(3000)

            return UserResponse(
                userId = 1,
                username = "cat",
                token = "catToken"
            )
        }

        delay(1000)
        throw IllegalArgumentException("Invalid username or password!!");
    }
}