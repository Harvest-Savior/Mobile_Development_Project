package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register/farmer")
    suspend fun postRegisterFarmer(
        @Field("namaLengkap") namaLengkap : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : RegisterFarmerResponse

    @FormUrlEncoded
    @POST("login/farmer")
    suspend fun postLoginFarmer(
        @Field("email") email : String,
        @Field("password") password: String
    ) : LoginFarmerResponse
}