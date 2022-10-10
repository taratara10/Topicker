package com.kabos.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*

class UserDataStore(
    private val userPreferences: DataStore<Preferences>,
) {
    private val UUID_KEY = stringPreferencesKey("uuid")

    suspend fun getUuid(): Flow<String> {
        return userPreferences.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[UUID_KEY] ?: setNewUuid()
        }
    }

    private suspend fun setNewUuid(): String {
        val randomUuid = UUID.randomUUID().toString()
        userPreferences.edit { preferences ->
            preferences[UUID_KEY] = randomUuid
        }
        return randomUuid
    }
}
