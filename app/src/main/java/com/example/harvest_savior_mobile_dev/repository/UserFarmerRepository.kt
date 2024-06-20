package com.example.harvest_savior_mobile_dev.repository

import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiService
import okhttp3.RequestBody

class UserFarmerRepository(private val apiService : ApiService) {

    suspend fun login(email: String, password : String) : LoginFarmerResponse {
        return apiService.postLoginFarmer(email,password)
    }

    suspend fun loginJspn(requestBody: RequestBody): LoginFarmerResponse {
        return apiService.postLoginFarmerJson(requestBody)
    }

    suspend fun register(namaLengkap : String, email: String, password: String ) : RegisterFarmerResponse {
        return apiService.postRegisterFarmer(namaLengkap,email,password)
    }

    suspend fun getDataLogin(token:String) : GetLoginFarmerResponse {
        return apiService.getDataFarmer("Bearer $token")
    }
}