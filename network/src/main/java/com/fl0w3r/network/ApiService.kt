package com.fl0w3r.network

import com.fl0w3r.model.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ApiService {
    // TODO Implement api.
    suspend fun isTokenValid(token: String): Boolean {
        delay(1000)

        return token.isNotEmpty()
    }

    suspend fun getToken(loginModel: LoginModel): String {
        if (loginModel.password == "cat" && loginModel.username == "cat") {
            delay(3000)

            return "apple";
        }

        delay(1000)
        throw IllegalArgumentException("Invalid username or password!!");
    }
}