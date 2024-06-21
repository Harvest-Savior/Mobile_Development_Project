package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DaftarPetaniViewModel(private val userFarmerRepository: UserFarmerRepository): ViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterFarmerResponse>>()
    val registerResult : LiveData<Result<RegisterFarmerResponse>> = _registerResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun registerFarmer(namaToko: RequestBody, email: RequestBody, pass: RequestBody, confP : RequestBody, photo: MultipartBody.Part) {
        _loading.value = true
        viewModelScope.launch {
            try {

                val response = userFarmerRepository.register(namaToko,email,pass,confP,photo)
                _registerResult.postValue(Result.success(response))
            } catch (e:Exception) {

                _registerResult.postValue(Result.failure(e))
            }finally {
                _loading.value = false
            }
        }
    }
}