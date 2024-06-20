package com.example.harvest_savior_mobile_dev.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.harvest_savior_mobile_dev.data.response.DataLoginStore
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastoreStore : DataStore<Preferences> by preferencesDataStore(name = "loginStore")
class LoginStorePreference(private val dataStore: DataStore<Preferences>) {
    val LOGIN_RESPONSE_KEY = stringPreferencesKey("login_store_response")
    private val GAMBAR_KEY = stringPreferencesKey("gambar")
    suspend fun saveLoginSession(loginResponse: LoginStoreResponse) {
        val jsonString = Gson().toJson(loginResponse)
        dataStore.edit { preferences ->
            preferences[LOGIN_RESPONSE_KEY] = jsonString
        }
    }

    fun getLoginSession() : Flow<LoginStoreResponse?> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[LOGIN_RESPONSE_KEY]
            if (!jsonString.isNullOrEmpty()) {
                Gson().fromJson(jsonString, LoginStoreResponse::class.java)
            } else {
                null
            }
        }
    }

    suspend fun saveGambar(gambar: String) {
        dataStore.edit { preferences ->
            preferences[GAMBAR_KEY] = gambar
        }
    }

    fun getGambar(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[GAMBAR_KEY]
        }
    }

    suspend fun removeLoginSession() {
        dataStore.edit { preferences ->
            preferences.remove(LOGIN_RESPONSE_KEY)
        }
    }


    companion object {
        @Volatile
        private var INSTANCE : LoginStorePreference? = null

        fun getInstance(dataStore: DataStore<Preferences>) : LoginStorePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = LoginStorePreference(dataStore)
                INSTANCE= instance
                instance
            }
        }
    }
}