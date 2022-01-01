package com.ddairy.eyebrows.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.ddairy.eyebrows.data.Eyebrow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val DATASTORE_PREFERENCES_NAME = "datastore_preferences"

class LocalDataStore(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_PREFERENCES_NAME)
    }

    val localEyebrows: Flow<String> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[LocalDataStoreKeys.LOCAL_EYEBROWS]?: InstanceMapper.mapper.writeValueAsString(emptyList<Eyebrow>())
        }

    // Needs to run in a Coroutine Scope, which is why it is 'suspend'.
    suspend fun writeLocalEyebrows(eyebrows: List<Eyebrow>) {
        context.dataStore.edit { preferences ->
            preferences[LocalDataStoreKeys.LOCAL_EYEBROWS] = InstanceMapper.mapper.writeValueAsString(eyebrows)
        }
    }
}