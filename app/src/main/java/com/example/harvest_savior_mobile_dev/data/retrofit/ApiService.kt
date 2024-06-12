package com.example.harvest_savior_mobile_dev.data.retrofit

import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun postRegister(

    )
}