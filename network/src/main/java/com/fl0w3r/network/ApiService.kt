package com.fl0w3r.network

import com.fl0w3r.model.LoginModel

class ApiService {
    // TODO Implement api.
    fun isTokenValid(token: String): Boolean {
        return token.isNotEmpty()
    }

    fun getToken(loginModel: LoginModel): String {
        if (loginModel.password == "cat" && loginModel.username == "cat") {
            return "apple";
        }

        throw IllegalArgumentException("Invalid username or password!!");
    }
}