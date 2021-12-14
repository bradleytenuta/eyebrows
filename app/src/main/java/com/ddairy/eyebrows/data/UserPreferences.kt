package com.ddairy.eyebrows.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"

class UserPreferences(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)
    }

    val isLight: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.IS_LIGHT_MODE]?: false
        }

    // Needs to run in a Coroutine Scope, which is why it is 'suspend'.
    suspend fun isLight(isLight: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LIGHT_MODE] = isLight
        }
    }
}