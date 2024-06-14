package com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel.LoginPenjualViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository

class LoginStoreVMFactory(private val medicineStoreRepository: MedicineStoreRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginPenjualViewModel::class.java)) {
            return LoginPenjualViewModel(medicineStoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}