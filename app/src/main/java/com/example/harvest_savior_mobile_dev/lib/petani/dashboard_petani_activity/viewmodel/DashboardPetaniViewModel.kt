package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import kotlinx.coroutines.launch

class DashboardPetaniViewModel(private val repo: UserFarmerRepository, private val loginPreference: LoginPreference) : ViewModel() {

    private val _loginResultData = MutableLiveData<Result<GetLoginFarmerResponse>>()
    val loginResultDat: LiveData<Result<GetLoginFarmerResponse>> = _loginResultData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loginData(token:String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resnponseData = repo.getDataLogin(token)
                _loginResultData.postValue(Result.success(resnponseData))
            }catch (e:Exception){
                _loginResultData.postValue(Result.failure(e))
            }finally {
                _loading.value = false
            }

        }
    }
}