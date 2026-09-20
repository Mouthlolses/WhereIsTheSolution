package com.whereisthesolution.whereisihesolutionapp.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val LOGGED_USER_ID = longPreferencesKey("logged_user_id")
    }

    val loggedUserId: Flow<Long?> =
        dataStore.data.map { preferences ->
            preferences[LOGGED_USER_ID]
        }

    suspend fun login(userId: Long) {
        dataStore.edit { preferences ->
            preferences[LOGGED_USER_ID] = userId
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(LOGGED_USER_ID)
        }
    }

}