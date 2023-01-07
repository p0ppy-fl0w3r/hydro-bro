package com.fl0w3r.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HydroPreferenceRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
        val LOGGED_IN_USER = stringPreferencesKey(name = "user_name")
    }

    suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ACCESS_TOKEN] = token
        }
    }

    val currentAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.ACCESS_TOKEN] ?: ""
    }

    suspend fun updateUser(username: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LOGGED_IN_USER] = username
        }
    }

    val loggedInUser: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.LOGGED_IN_USER] ?: ""
    }

}