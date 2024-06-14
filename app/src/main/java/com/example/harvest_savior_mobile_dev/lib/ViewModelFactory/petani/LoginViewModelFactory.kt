package com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference

class LoginViewModelFactory(private val userFarmerRepository: UserFarmerRepository,private val preference: LoginPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginPetaniViewModel::class.java)) {
            return LoginPetaniViewModel(userFarmerRepository,preference) as T
        }

        throw IllegalArgumentException("Unknown View Model Class:" + modelClass.name)
    }
}