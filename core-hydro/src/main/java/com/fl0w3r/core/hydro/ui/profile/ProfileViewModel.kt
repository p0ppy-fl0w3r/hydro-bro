package com.fl0w3r.core.hydro.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.database.HydroDatabase
import com.fl0w3r.core.data.datastore.HydroPreferenceRepository
import com.fl0w3r.core.hydro.ui.profile.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val hydroPreferenceRepository: HydroPreferenceRepository,
    private val database: HydroDatabase
) : ViewModel() {

    private val _profileUiSate = MutableLiveData<ProfileUiState>()
    val profileUiState: LiveData<ProfileUiState>
        get() = _profileUiSate

    init {
        getUiState()
    }

    private fun getUiState() {
        viewModelScope.launch {
            hydroPreferenceRepository.loggedInUser.collectLatest {
                val user = database.hydroDao.getUser(it)
                _profileUiSate.value = ProfileUiState(
                    username = user.username,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    age = user.age,
                    email = user.email
                )
            }
        }
    }

    suspend fun clearToken() {
        hydroPreferenceRepository.updateToken("")
    }

}