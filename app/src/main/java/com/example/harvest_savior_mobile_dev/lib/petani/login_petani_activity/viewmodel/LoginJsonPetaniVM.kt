package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.DeteksiGetTokenResponse
import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginPetaniRequest
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class LoginJsonPetaniVM(private val repo: UserFarmerRepository, private val loginPreference: LoginPreference):ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginFarmerResponse>>()
    val loginResult: LiveData<Result<LoginFarmerResponse>> = _loginResult

    private val _getTokenResult = MutableLiveData<Result<DeteksiGetTokenResponse>>()
    val getTokenResult : LiveData<Result<DeteksiGetTokenResponse>> = _getTokenResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    var tokenDeteksi: String? = null

    fun loginAndFetchToken(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {

                val loginDeferred = async { repo.loginJspn(LoginPetaniRequest(email, password).toRequestBody()) }
                val tokenDeferred = async { repo.getTokenDeteksi(email, password) }

                val loginResponse = loginDeferred.await()
                val tokenResponse = tokenDeferred.await()

                _loginResult.postValue(Result.success(loginResponse))
                _getTokenResult.postValue(Result.success(tokenResponse))

                tokenDeteksi = tokenResponse.accessToken
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
                _getTokenResult.postValue(Result.failure(e))
            } finally {
                _loading.value = false
            }
        }
    }

    private fun LoginPetaniRequest.toRequestBody(): RequestBody {
        val json = Gson().toJson(this)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun getTokenDeteksi(): LiveData<String?> {
        return loginPreference.getTokenDeteksi().asLiveData()
    }
    fun saveTokenDeteksi(isLoged : String) {
        viewModelScope.launch {
            loginPreference.saveTokenDeteksi(isLoged)
        }
    }


    fun getLoginSession(): LiveData<LoginFarmerResponse?> {
        return loginPreference.getLoginSession().asLiveData()
    }

    fun saveLoginSession(isLoged : LoginFarmerResponse) {
        viewModelScope.launch {
            loginPreference.saveLoginSession(isLoged)
        }
    }
}