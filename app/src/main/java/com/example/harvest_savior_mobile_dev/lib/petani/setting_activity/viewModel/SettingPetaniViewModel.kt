package com.example.harvest_savior_mobile_dev.lib.petani.setting_activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import kotlinx.coroutines.launch

class SettingPetaniViewModel(private val userFarmerRepository: UserFarmerRepository,private val pref : LoginPreference) : ViewModel() {
    fun getLoginSession(): LiveData<LoginFarmerResponse?> {
        return pref.getLoginSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.removeLoginSession()
        }
    }
}