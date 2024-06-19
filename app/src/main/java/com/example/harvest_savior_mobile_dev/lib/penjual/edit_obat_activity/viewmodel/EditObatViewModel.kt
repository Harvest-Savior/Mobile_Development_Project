package com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.EditObatResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditObatViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val pref : LoginStorePreference): ViewModel() {
    private val _editObatResult = MutableLiveData<Result<EditObatResponse>>()
    val editObatResult : LiveData<Result<EditObatResponse>> = _editObatResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun editObat(tokenP : String, idObat : String,desk:RequestBody?,nama:RequestBody?,stok:Int?,harga:Int?,photo: RequestBody?) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = medicineStoreRepository.editObat(tokenP,idObat,desk,nama,stok,harga,photo)
                _editObatResult.postValue(Result.success(response))

            } catch (e : Exception) {
                _editObatResult.postValue(Result.failure(e))

            } finally {
                _loading.value = false
            }
        }
    }
}