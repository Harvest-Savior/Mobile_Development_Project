package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.data.response.AddObatResponse
import com.example.harvest_savior_mobile_dev.data.response.EditObatResponse
import com.example.harvest_savior_mobile_dev.data.response.GetLoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.GetObatResponse
import com.example.harvest_savior_mobile_dev.data.response.HapusObatResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
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

    @Multipart
    @POST("register/store")
    suspend fun postRegisterStore(
        @Part("namaToko") namaToko : RequestBody,
        @Part("email") email : RequestBody,
        @Part("alamat") alamat : RequestBody,
        @Part("noHp") noHp : RequestBody,
        @Part("password") password : RequestBody,
        @Part("confPassword") confPassword : RequestBody,
        @Part photo : MultipartBody.Part?
    ) : RegisterStoreResponse

    @FormUrlEncoded
    @POST("login/store")
    suspend fun postLoginStore(
        @Field("email") email : String,
        @Field("password") password: String
    ) : LoginStoreResponse

    @POST("login/store")
    suspend fun postLoginStoreJson(
        @Body requestBody: RequestBody
    ): LoginStoreResponse

    @POST("login/farmer")
    suspend fun postLoginFarmerJson(
        @Body requestBody: RequestBody
    ): LoginFarmerResponse

    @GET("users/farmer")
    suspend fun getDataFarmer(
        @Header("Authorization") token: String,
    ) : GetLoginFarmerResponse

    @Multipart
    @POST("addMedicine")
    suspend fun addObat (
        @Header("Authorization") token: String,
        @Part("namaObat") namaObat : RequestBody?,
        @Part("deskripsi") deskripsi : RequestBody?,
        @Part("harga") harga : RequestBody?,
        @Part("stok") stok : RequestBody?,
        @Part("penyakit") tipePenyakit : RequestBody?,
        @Part photo: MultipartBody.Part?,
        @Part("linkProduct") linkProduk : RequestBody?
    ) : AddObatResponse

    @GET("getMedicines")
    suspend fun getObat (
        @Header("Authorization") token: String
    ) : GetObatResponse

    @DELETE("delMedicines/{id}")
    suspend fun deleteObat(
        @Header("Authorization") token: String,
        @Path("id") idObat : String
    ) : HapusObatResponse

    @Multipart
    @PUT("updateMedicines/{id}")
    suspend fun editObat (
        @Header("Authorization") token: String,
        @Path("id") idObat: String,
        @Part("namaObat") namaObat : RequestBody?,
        @Part("deskripsi") deskripsi : RequestBody?,
        @Part("harga") harga : RequestBody?,
        @Part("stok") stok : RequestBody?,
        @Part("penyakit") tipePenyakit: RequestBody?,
        @Part photo: MultipartBody.Part?,
        @Part("linkProduct") linkProduk: RequestBody?
    ) : EditObatResponse

}