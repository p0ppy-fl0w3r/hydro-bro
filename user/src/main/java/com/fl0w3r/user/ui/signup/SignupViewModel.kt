package com.fl0w3r.user.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.model.RegisterModel
import com.fl0w3r.network.HydroApi
import com.fl0w3r.user.ui.signup.state.ApiStatus
import com.fl0w3r.user.ui.signup.state.SignupApiState
import com.fl0w3r.user.ui.signup.state.SignupErrorState
import com.fl0w3r.user.ui.signup.state.SignupState
import kotlinx.coroutines.launch
import retrofit2.HttpException


class SignupViewModel : ViewModel() {
    private val apiService = HydroApi.hydroApiService

    private val _signupState = MutableLiveData<SignupState>()
    val signupState: LiveData<SignupState>
        get() = _signupState

    private val _signupErrorState = MutableLiveData<SignupErrorState>()
    val signupErrorState: LiveData<SignupErrorState>
        get() = _signupErrorState

    private val _signupApiState = MutableLiveData<SignupApiState>()
    val signupApiState: LiveData<SignupApiState>
        get() = _signupApiState

    fun onStateChange(state: SignupState) {
        _signupState.value = state
    }

    fun onSignUp(state: SignupState) {
        viewModelScope.launch {
            if (validateFields(state)) {
                _signupApiState.value = SignupApiState(
                    status = ApiStatus.PENDING,
                    message = ""
                )
                try {
                    apiService.registerUser(
                        RegisterModel(
                            username = state.username,
                            firstName = state.firstName,
                            lastName = state.lastName,
                            email = state.email,
                            password = state.password,
                            age = state.age!!
                        )
                    )
                    _signupApiState.value = SignupApiState(
                        status = ApiStatus.SUCCESS,
                        message = "Registered new user."
                    )
                } catch (e: HttpException) {
                    _signupApiState.value = SignupApiState(
                        status = ApiStatus.FAILED,
                        message = e.response()?.errorBody()?.string().toString()
                    )
                } catch (e: Exception) {
                    _signupApiState.value = SignupApiState(
                        status = ApiStatus.FAILED,
                        message = "Could not sign up at the moment."
                    )
                }
            }
        }
    }

    fun resetApiState() {
        _signupApiState.value = SignupApiState(
            status = ApiStatus.INITIAL,
            message = ""
        )
    }

    private fun validateFields(state: SignupState): Boolean {
        val errorState = SignupErrorState()
        var isValid = true
        state.apply {
            if (username.length < 3) {
                isValid = false
                errorState.usernameError = "Username must be at least three characters!"
            }

            if (firstName.length < 3) {
                isValid = false
                errorState.firstNameError = "First name must be at least three characters!"
            }

            if (lastName.length < 3) {
                isValid = false
                errorState.lastNameError = "Last name must be at least three characters!"
            }
            if (!email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) {
                isValid = false
                errorState.emailError = "Email is not valid!"
            }
            if (password.length < 6) {
                isValid = false
                errorState.passwordError = "Password must be at least 6 characters."
            }
            if (age == null) {
                isValid = false
                errorState.ageError = "Age is required!"
            } else {
                if (age < 12 || age > 120) {
                    isValid = false
                    errorState.ageError = "Age should be between 12 and 120."
                }
            }
        }

        _signupErrorState.value = errorState

        return isValid
    }

}