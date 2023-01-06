package com.fl0w3r.core.hydro.ui.profile

import androidx.lifecycle.ViewModel
import com.fl0w3r.core.data.datastore.HydroPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val hydroPreferenceRepository: HydroPreferenceRepository) :
    ViewModel() {

    suspend fun clearToken() {
        hydroPreferenceRepository.updateToken("")
    }

}