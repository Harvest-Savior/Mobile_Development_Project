package com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference

class HomePenjualViewModel(private val medicineStoreRepository: MedicineStoreRepository, private val loginStorePreference: LoginStorePreference): ViewModel() {

}