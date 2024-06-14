package com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import kotlinx.coroutines.launch

class DaftarPenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository): ViewModel() {
    private val _registerResult = MutableLiveData<Result<RegisterStoreResponse>>()
    val registerResult : LiveData<Result<RegisterStoreResponse>> = _registerResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun registerStore(namaToko:String,alamat: String, email:String, noHp : Int , pass:String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                _loading.value = false
                val response = medicineStoreRepository.register(namaToko,alamat,email,noHp,pass)
                _registerResult.postValue(Result.success(response))
            } catch (e:Exception) {
                _loading.value = false
                _registerResult.postValue(Result.failure(e))
            }
        }
    }
}