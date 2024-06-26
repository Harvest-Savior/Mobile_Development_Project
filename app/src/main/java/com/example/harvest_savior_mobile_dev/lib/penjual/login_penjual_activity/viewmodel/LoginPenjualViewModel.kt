package com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.launch

class LoginPenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val loginStorePreference: LoginStorePreference): ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginStoreResponse>>()
    val loginResult : LiveData<Result<LoginStoreResponse>> = _loginResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = medicineStoreRepository.login(email, pass)
                _loginResult.postValue(Result.success(response))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            } finally {
                _loading.value = false
            }
        }
    }

    fun getLoginSession(): LiveData<LoginStoreResponse?> {
        return loginStorePreference.getLoginSession().asLiveData()
    }

    fun saveLoginSession(isLoged : LoginStoreResponse) {
        viewModelScope.launch {
            loginStorePreference.saveLoginSession(isLoged)
        }
    }
}