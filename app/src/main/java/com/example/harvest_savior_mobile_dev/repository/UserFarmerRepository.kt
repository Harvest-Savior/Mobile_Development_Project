package com.example.harvest_savior_mobile_dev.repository

import com.example.harvest_savior_mobile_dev.data.response.DeteksiGetTokenResponse
import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.GetRiwayatDeteksiResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiService
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiServiceDeteksi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserFarmerRepository(private val apiService : ApiService?,private val apiService2 : ApiServiceDeteksi?) {

    suspend fun login(email: String, password : String) : LoginFarmerResponse {
        return apiService?.postLoginFarmer(email,password)!!
    }

    suspend fun loginJspn(requestBody: RequestBody): LoginFarmerResponse {
        return apiService?.postLoginFarmerJson(requestBody)!!
    }

    suspend fun getTokenDeteksi(username:String?,pass:String?) : DeteksiGetTokenResponse {
        return apiService2?.getTokenPrediksi(username,pass)!!
    }

    suspend fun register(namaLengkap : RequestBody?, email: RequestBody?, password: RequestBody?,confP:RequestBody?,photo:MultipartBody.Part?
     ) : RegisterFarmerResponse {
        return apiService?.postRegisterFarmer(namaLengkap,email,password,confP,photo)!!
    }

    suspend fun getDataLogin(token:String) : GetLoginFarmerResponse {
        return apiService?.getDataFarmer("Bearer $token")!!
    }

    suspend fun getHistoriDeteksi(token: String) :GetRiwayatDeteksiResponse {
        return apiService2?.getHistoryDekteksi("Bearer $token")!!
    }
}