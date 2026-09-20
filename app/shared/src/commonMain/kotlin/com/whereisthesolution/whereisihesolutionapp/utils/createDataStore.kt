package com.whereisthesolution.whereisihesolutionapp.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(): DataStore<Preferences>