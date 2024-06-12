package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import kotlinx.coroutines.launch

class DaftarPetaniViewModel(private val userFarmerRepository: UserFarmerRepository): ViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterFarmerResponse>>()
    val registerResult : LiveData<Result<RegisterFarmerResponse>> = _registerResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun registerFarmer(nama:String, email:String,pass:String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                _loading.value = false
                val response = userFarmerRepository.register(nama,email,pass)
                _registerResult.postValue(Result.success(response))
            } catch (e:Exception) {
                _loading.value = false
                _registerResult.postValue(Result.failure(e))
            }
        }
    }
}