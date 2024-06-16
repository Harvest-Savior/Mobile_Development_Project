package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.data.response.AddObatResponse
import com.example.harvest_savior_mobile_dev.data.response.EditObatResponse
import com.example.harvest_savior_mobile_dev.data.response.GetObatResponse
import com.example.harvest_savior_mobile_dev.data.response.HapusObatResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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

    @Multipart
    @POST("medicines")
    suspend fun addObat (
        @Header("Authorization") token: String,
        @Part("namaObat") namaObat : String,
        @Part("deskripsi") deskripsi : String,
        @Part("stok") stok : Int,
        @Part("harga") harga : Int,
        @Part photo: MultipartBody.Part?
    ) : AddObatResponse

    @GET("getMedicines")
    suspend fun getObat (
        @Header("Authorization") token: String
    ) : GetObatResponse

    @DELETE("delmedicines/{id}")
    suspend fun deleteObat(
        @Header("Authorization") token: String,
        @Path("id") idObat : String
    ) : HapusObatResponse

    @PUT("updateMedicines/{id}")
    suspend fun editObat (
        @Header("Authorization") token: String,
        @Part("id") idObat: String
    ) : EditObatResponse

}