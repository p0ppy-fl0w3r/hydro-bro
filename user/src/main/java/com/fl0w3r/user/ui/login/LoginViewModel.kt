package com.fl0w3r.user.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fl0w3r.model.LoginModel
import com.fl0w3r.network.ApiService
import com.fl0w3r.user.ui.login.state.TokenState
import java.lang.IllegalArgumentException


class LoginViewModel() : ViewModel() {

    private val _tokenState = MutableLiveData<TokenState>()
    val tokenState: LiveData<TokenState>
        get() = _tokenState

    private val apiService = ApiService()

    fun authenticateUser(loginModel: LoginModel) {
        try {
            val token = apiService.getToken(loginModel)
            _tokenState.value = TokenState(
                isValid = true,
                token = token
            )
        } catch (e: IllegalArgumentException) {
            _tokenState.value = TokenState(
                isValid = false,
                token = "",
                errorMessage = e.message.toString()
            )
        }

    }

}