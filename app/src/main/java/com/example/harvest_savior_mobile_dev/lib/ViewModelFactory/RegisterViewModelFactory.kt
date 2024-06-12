package com.example.harvest_savior_mobile_dev.lib.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository

class RegisterViewModelFactory(private val userFarmerRepository: UserFarmerRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DaftarPetaniViewModel::class.java)) {
            return DaftarPetaniViewModel(userFarmerRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class:" + modelClass.name)
    }


}