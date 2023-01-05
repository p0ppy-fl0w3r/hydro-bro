package com.fl0w3r.core.hydro.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.datastore.HydroPreferenceRepositiry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val hydroPreferenceRepository: HydroPreferenceRepositiry) :
    ViewModel() {

    suspend fun clearToken() {
        hydroPreferenceRepository.updateToken("")
    }

}