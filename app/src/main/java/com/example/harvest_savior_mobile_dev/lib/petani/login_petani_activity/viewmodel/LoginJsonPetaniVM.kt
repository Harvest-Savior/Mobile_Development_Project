package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginPetaniRequest
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginJsonPetaniVM(private val repo: UserFarmerRepository, private val loginPreference: LoginPreference):ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginFarmerResponse>>()
    val loginResult: LiveData<Result<LoginFarmerResponse>> = _loginResult



    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val loginRequest = LoginPetaniRequest(email, password)
                val json = Gson().toJson(loginRequest)
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                val response = repo.loginJspn(requestBody)

                _loginResult.postValue(Result.success(response))

            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            } finally {
                _loading.value = false
            }
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