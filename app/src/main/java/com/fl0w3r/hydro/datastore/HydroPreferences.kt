package com.fl0w3r.hydro.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HydroPreferences(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "token_pref"
        )

        val ACCESS_TOKEN = stringPreferencesKey(name = "access_token")

    }

    suspend fun updateToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    val currentAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN] ?: ""
    }

}