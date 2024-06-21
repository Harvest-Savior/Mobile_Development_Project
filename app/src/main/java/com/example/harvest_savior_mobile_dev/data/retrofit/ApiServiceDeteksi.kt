package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.data.response.DeteksiGetTokenResponse
import com.example.harvest_savior_mobile_dev.data.response.DeteksiResponse
import com.example.harvest_savior_mobile_dev.data.response.GetRiwayatDeteksiResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceDeteksi {

    @Multipart
    @POST("predict_image")
    suspend fun prediksi (
        @Header("Authorization") token:String?,
        @Part photo : MultipartBody.Part?
    ) : DeteksiResponse

    @FormUrlEncoded
    @POST("token")
    suspend fun getTokenPrediksi(
        @Field("username") username:String?,
        @Field("password") password : String?
    ) : DeteksiGetTokenResponse

    @GET("user_predictions")
    suspend fun getHistoryDekteksi(
        @Header("Authorization") token:String?,
    ) : GetRiwayatDeteksiResponse

}