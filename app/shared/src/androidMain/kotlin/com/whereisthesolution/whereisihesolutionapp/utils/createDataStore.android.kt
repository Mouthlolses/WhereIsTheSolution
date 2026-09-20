package com.whereisthesolution.whereisihesolutionapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


private lateinit var appContext: Context

fun initDataStore(context: Context) {
    appContext = context.applicationContext
}
actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            appContext.filesDir
                .resolve("app.preferences_pb")
                .absolutePath
                .toPath()
        }
    )
}