package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import kotlinx.coroutines.launch

class LoginPetaniViewModel(private val userFarmerRepository: UserFarmerRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginFarmerResponse>>()
    val loginResult : LiveData<Result<LoginFarmerResponse>> = _loginResult

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val response = userFarmerRepository.login(email,pass)
                _loginResult.postValue(Result.success(response))
            } catch (e : Exception) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}