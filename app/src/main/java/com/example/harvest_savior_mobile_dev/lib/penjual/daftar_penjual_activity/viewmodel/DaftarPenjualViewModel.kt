package com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DaftarPenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository): ViewModel() {
    private val _registerResult = MutableLiveData<Result<RegisterStoreResponse>>()
    val registerResult : LiveData<Result<RegisterStoreResponse>> = _registerResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun registerStore(namaToko:RequestBody, email:RequestBody,alamat: RequestBody, noHp : RequestBody , pass:RequestBody,confP : RequestBody, photo:MultipartBody.Part) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = medicineStoreRepository.register(namaToko,email,alamat,noHp,pass,confP,photo)
                _registerResult.postValue(Result.success(response))
            } catch (e:Exception) {
                _registerResult.postValue(Result.failure(e))
            }finally {
                _loading.value = false
            }
        }
    }
}