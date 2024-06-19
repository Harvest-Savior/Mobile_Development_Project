package com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.AddObatResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class TambahObatViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val pref : LoginStorePreference):ViewModel() {

    private val _addObatResult = MutableLiveData<Result<AddObatResponse>>()
    val addObatResult : LiveData<Result<AddObatResponse>> = _addObatResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun addObat(tokenP:String, nama : RequestBody?, desk : RequestBody?, stok:RequestBody?, harga:RequestBody?, photo:MultipartBody.Part?) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = medicineStoreRepository.addObat(tokenP,nama,desk,stok,harga,photo)
                _addObatResult.postValue(Result.success(response))

            }catch (e : Exception) {
                _addObatResult.postValue(Result.failure(e))

            }finally {
                _loading.value = false
            }
        }
    }
}