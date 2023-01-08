package com.fl0w3r.user.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fl0w3r.network.HydroApi
import com.fl0w3r.user.ui.signup.state.SignupErrorState
import com.fl0w3r.user.ui.signup.state.SignupState



class SignupViewModel : ViewModel() {
    private val apiService = HydroApi.hydroApiService

    private val _signupState = MutableLiveData<SignupState>()
    val signupState: LiveData<SignupState>
        get() = _signupState

    private val _signupErrorState = MutableLiveData<SignupErrorState>()
    val signupErrorState: LiveData<SignupErrorState>
        get() = _signupErrorState

    fun onStateChange(state: SignupState) {
        _signupState.value = state
    }

}