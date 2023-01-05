package com.fl0w3r.user.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.datastore.HydroPreferenceRepositiry
import com.fl0w3r.model.LoginModel
import com.fl0w3r.network.ApiService
import com.fl0w3r.user.ui.login.state.TokenState
import com.fl0w3r.user.ui.login.state.TokenValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val hydroPreferenceRepository: HydroPreferenceRepositiry) :
    ViewModel() {

    private val _tokenState = MutableLiveData<TokenState>()
    val tokenState: LiveData<TokenState>
        get() = _tokenState

    private val apiService = ApiService()

    init {
        checkTokenValidity()

    }

    private fun checkTokenValidity() {
        viewModelScope.launch {
            val currentToken = hydroPreferenceRepository.currentAccessToken
            currentToken.collectLatest {
                val tokenValid = apiService.isTokenValid(it)
                if (tokenValid) {
                    _tokenState.value = TokenState(
                        validity = TokenValid.FROM_STORE, token = ""
                    )
                } else {
                    // Token validation failed.
                    // Either the user is logging in for the first time or their token has expired.
                    _tokenState.value = TokenState(
                        validity = if (it.isNotEmpty()) TokenValid.INVALID else TokenValid.FRESH_LOGIN,
                        token = "",
                        errorMessage = "Failed to login!"
                    )
                }
            }
        }
    }

    fun updateCurrentToken(token: String) {
        viewModelScope.launch {
            hydroPreferenceRepository.updateToken(token)
        }
    }

    fun authenticateUser(loginModel: LoginModel) {
        try {
            val token = apiService.getToken(loginModel)
            _tokenState.value = TokenState(
                validity = TokenValid.FROM_API, token = token
            )
        } catch (e: IllegalArgumentException) {
            _tokenState.value = TokenState(
                validity = TokenValid.INVALID, token = "", errorMessage = e.message.toString()
            )
        }

    }

}