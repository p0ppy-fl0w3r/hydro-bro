package com.fl0w3r.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HydroPreferenceRepositiry @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
    }

    suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ACCESS_TOKEN] = token
        }
    }

    val currentAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.ACCESS_TOKEN] ?: ""
    }

}