package com.fl0w3r.user.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.database.HydroDatabase
import com.fl0w3r.core.data.database.entity.User
import com.fl0w3r.core.data.datastore.HydroPreferenceRepository
import com.fl0w3r.model.LoginModel
import com.fl0w3r.network.HydroApi
import com.fl0w3r.user.ui.login.state.TokenState
import com.fl0w3r.user.ui.login.state.TokenValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val hydroPreferenceRepository: HydroPreferenceRepository,
    private val database: HydroDatabase
) : ViewModel() {

    private val _tokenState = MutableLiveData<TokenState>()
    val tokenState: LiveData<TokenState>
        get() = _tokenState

    private val apiService = HydroApi.hydroApiService

    init {
        checkTokenValidity()
    }

    private fun checkTokenValidity() {
        viewModelScope.launch {
            val currentToken = hydroPreferenceRepository.currentAccessToken
            currentToken.collectLatest {
                try {
                    val tokenValid = apiService.isTokenValid("Bearer $it")
                    if (tokenValid) {
                        _tokenState.value = TokenState(
                            validity = TokenValid.FROM_STORE, token = ""
                        )
                    }
                } catch (e: Exception) {
                    // Token validation failed.
                    // Either the user is logging in for the first time or their token has expired.
                    _tokenState.value = TokenState(
                        validity = if (it.isNotEmpty()) TokenValid.INVALID else TokenValid.FRESH_LOGIN,
                        token = "",
                        errorMessage = if (it.isNotEmpty()) "Failed to login!" else ""
                    )
                }
            }
        }
    }

    fun authenticateUser(loginModel: LoginModel) {
        viewModelScope.launch {
            try {
                val userResponse = apiService.loginUser(loginModel)

                database.hydroDao.addUser(
                    User(
                        username = userResponse.username,
                        firstName = userResponse.firstName,
                        lastName = userResponse.lastName,
                        age = userResponse.age,
                        email = userResponse.email
                    )
                )

                hydroPreferenceRepository.updateUser(userResponse.username)

                _tokenState.value = TokenState(
                    validity = TokenValid.FROM_API, token = userResponse.token
                )

                hydroPreferenceRepository.updateToken(userResponse.token)

            } catch (e: HttpException) {
                _tokenState.value = TokenState(
                    validity = TokenValid.INVALID,
                    token = "",
                    errorMessage = e.response()?.errorBody()?.string().toString()
                )

            } catch (e: Exception) {
                _tokenState.value = TokenState(
                    validity = TokenValid.INVALID,
                    token = "",
                    errorMessage = "Unable to login right now :("
                )
            }
        }
    }

    fun resetErrorMessage(tokenState: TokenState) {
        _tokenState.value = tokenState.copy(errorMessage = "")
    }

}