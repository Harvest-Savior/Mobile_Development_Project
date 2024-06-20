package com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.launch

class SettingPenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val loginStorePref : LoginStorePreference): ViewModel() {

    fun getLoginSession(): LiveData<LoginStoreResponse?> {
        return loginStorePref.getLoginSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            loginStorePref.removeLoginSession()
        }
    }

    fun getGambar(): LiveData<String?> {
        return loginStorePref.getGambar().asLiveData()
    }
}