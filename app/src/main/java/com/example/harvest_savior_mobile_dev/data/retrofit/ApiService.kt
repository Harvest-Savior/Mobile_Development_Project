package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
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

    @FormUrlEncoded
    @POST("register/medicine-store")
    suspend fun postRegisterStore(
        @Field("namaToko") namaToko : String,
        @Field("alamat") alamat : String,
        @Field("email") email : String,
        @Field("noHp") noHp : Int,
        @Field("password") password : String
    ) : RegisterStoreResponse

    @FormUrlEncoded
    @POST("login/medicine-store")
    suspend fun postLoginStore(
        @Field("email") email : String,
        @Field("password") password: String
    ) : LoginStoreResponse
}