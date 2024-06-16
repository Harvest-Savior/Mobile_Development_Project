package com.example.harvest_savior_mobile_dev.repository

import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiService
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference

class MedicineStoreRepository(private val apiService: ApiService, private val preference: LoginStorePreference ) {
    suspend fun login(email: String, password : String) : LoginStoreResponse {
        return apiService.postLoginStore(email,password)
    }

    suspend fun register(namaToko : String, alamat : String, email: String, noHp : Int, password: String ) : RegisterStoreResponse {
        return apiService.postRegisterStore(namaToko,alamat,email,noHp,password)
    }

}