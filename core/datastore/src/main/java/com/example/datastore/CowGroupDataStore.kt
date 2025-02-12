package com.example.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TOKEN_DATASTORE = "token_datastore"
private const val LOGIN_CHECK_DATASTORE = "login_check_datastore"

private val Context.tokenDataStore by preferencesDataStore(name = TOKEN_DATASTORE)
private val Context.loginCheckDataStore by preferencesDataStore(name = LOGIN_CHECK_DATASTORE)

class CowGroupDataStore(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val LOGIN_CHECK = booleanPreferencesKey("login_check")
    }

    suspend fun saveToken(token: String) {
        if (token.isNotEmpty()) {
            context.tokenDataStore.edit { preferences ->
                preferences[TOKEN_KEY] = token
            }
            context.loginCheckDataStore.edit { preferences ->
                preferences[LOGIN_CHECK] = true
            }
        }
    }

    val token: Flow<String?> = context.tokenDataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    suspend fun getToken(): String? = context.tokenDataStore.data.first()[TOKEN_KEY]

    suspend fun loginCheck(): Boolean = context.loginCheckDataStore.data.first()[LOGIN_CHECK] ?: false

    suspend fun clearToken() {
        context.tokenDataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
        context.loginCheckDataStore.edit { preferences ->
            preferences.remove(LOGIN_CHECK)
        }
    }
}
