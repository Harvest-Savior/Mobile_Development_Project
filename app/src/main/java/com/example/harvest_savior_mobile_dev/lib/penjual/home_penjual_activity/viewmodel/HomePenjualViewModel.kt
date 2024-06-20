package com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.GetObatResponse
import com.example.harvest_savior_mobile_dev.data.response.HapusObatResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreRequest
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class HomePenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val loginStorePreference: LoginStorePreference): ViewModel() {
    private val _getObatResult = MutableLiveData<Result<GetObatResponse>>()
    val getObatResult : LiveData<Result<GetObatResponse>> = _getObatResult

    private val _hapusObatResult = MutableLiveData<Result<HapusObatResponse>>()
    val hapusObatResult : LiveData<Result<HapusObatResponse>> = _hapusObatResult

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _profileImageUrl = MutableLiveData<String>()
    val profileImageUrl: LiveData<String> = _profileImageUrl

    private val _loginResult = MutableLiveData<Result<LoginStoreResponse>>()
    val loginResult: LiveData<Result<LoginStoreResponse>> = _loginResult

    fun getObat(tokenV : String?) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = medicineStoreRepository.getObat(tokenV)
                _getObatResult.postValue(Result.success(response))
                _loading.value = false
            } catch (e : Exception) {
                _getObatResult.postValue(Result.failure(e))
                _loading.value = false
            }
        }
    }


    fun deleteObat(tokenP : String, idObat:String) {
        _loading.value = true
        viewModelScope.launch{
            try {
                val response = medicineStoreRepository.deteleObat(tokenP,idObat)
                _hapusObatResult.postValue(Result.success(response))

            }catch (e:Exception) {

                _hapusObatResult.postValue(Result.failure(e))
            }finally {
                _loading.value = false
            }
        }
    }

    fun getGambar(): LiveData<String?> {
        return loginStorePreference.getGambar().asLiveData()
    }

}