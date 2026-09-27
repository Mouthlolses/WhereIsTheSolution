package com.whereisthesolution.whereisihesolutionapp.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SessionManager(
    private val dataStore: DataStore<Preferences>
) {


    private companion object {
        val LOGGED_USER_ID = longPreferencesKey("logged_user_id")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")


    }

    val loggedUserId: Flow<Long?> =
        dataStore.data.map { preferences ->
            preferences[LOGGED_USER_ID]
        }

    suspend fun login(
        userId: Long,
        token: String
    ) {
        dataStore.edit { preferences ->
            preferences[LOGGED_USER_ID] = userId
            preferences[AUTH_TOKEN] = token
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data
            .map { preferences ->
                preferences[AUTH_TOKEN]
            }
            .first()
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(LOGGED_USER_ID)
            preferences.remove(AUTH_TOKEN)
        }
    }

}