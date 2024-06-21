package com.example.harvest_savior_mobile_dev.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "login")


open class LoginPreference(private val dataStore: DataStore<Preferences>) {
    val LOGIN_RESPONSE_KEY = stringPreferencesKey("login_response")
    private val TOKEN_DETEKSI_KEY = stringPreferencesKey("token_deteksi_key")

    suspend fun saveLoginSession(token: LoginFarmerResponse) {
        val jsonString = Gson().toJson(token)
        dataStore.edit { preference ->
            preference[LOGIN_RESPONSE_KEY] = jsonString
        }

    }

    fun getLoginSession() : Flow<LoginFarmerResponse?> {
        return dataStore.data.map { preference ->
            val jsonString = preference[LOGIN_RESPONSE_KEY]
            Gson().fromJson(jsonString, LoginFarmerResponse::class.java)
        }
    }

    suspend fun removeLoginSession() {
        dataStore.edit { preference ->
            preference.remove(LOGIN_RESPONSE_KEY)
        }
    }

    suspend fun saveTokenDeteksi(tokenDeteksi: String) {
        dataStore.edit { preference ->
            preference[TOKEN_DETEKSI_KEY] = tokenDeteksi
        }
    }

    fun getTokenDeteksi(): Flow<String?> {
        return dataStore.data.map { preference ->
            preference[TOKEN_DETEKSI_KEY]
        }
    }


    companion object {
        @Volatile
        private var INSTANCE : LoginPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>) : LoginPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = LoginPreference(dataStore)
                INSTANCE= instance
                instance
            }
        }
    }
}