package com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel.HomePenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel.LoginPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.viewModel.SettingPenjualViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference

class LoginStoreVMFactory(private val medicineStoreRepository: MedicineStoreRepository, private val loginStorePreference: LoginStorePreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginPenjualViewModel::class.java)) {
            return LoginPenjualViewModel(medicineStoreRepository, loginStorePreference) as T
        } else if (modelClass.isAssignableFrom(HomePenjualViewModel::class.java)) {
            return HomePenjualViewModel(medicineStoreRepository,loginStorePreference) as T
        }else if (modelClass.isAssignableFrom(SettingPenjualViewModel::class.java)) {
            return SettingPenjualViewModel(medicineStoreRepository,loginStorePreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}