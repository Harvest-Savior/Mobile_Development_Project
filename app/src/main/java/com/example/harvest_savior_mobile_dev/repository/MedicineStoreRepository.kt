package com.example.harvest_savior_mobile_dev.repository

import android.util.Log
import com.example.harvest_savior_mobile_dev.data.response.AddObatResponse
import com.example.harvest_savior_mobile_dev.data.response.EditObatResponse
import com.example.harvest_savior_mobile_dev.data.response.GetObatResponse
import com.example.harvest_savior_mobile_dev.data.response.HapusObatResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.RegisterStoreResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiService
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiServiceDeteksi
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MedicineStoreRepository(private val apiService: ApiService?,private val apiService2:ApiServiceDeteksi?,private val preference: LoginStorePreference ) {
    suspend fun login(email: String, password : String) : LoginStoreResponse {
        return apiService?.postLoginStore(email,password)!!
    }

    suspend fun loginJspn(requestBody: RequestBody): LoginStoreResponse {
        return apiService?.postLoginStoreJson(requestBody)!!
    }

    suspend fun register(namaToko : RequestBody,email: RequestBody, alamat : RequestBody,  noHp : RequestBody, password: RequestBody,confP : RequestBody, photo : MultipartBody.Part ) : RegisterStoreResponse {
        return apiService?.postRegisterStore(namaToko,email,alamat,noHp,password, confP,photo)!!
    }

    suspend fun getObat(tokenP : String?) : GetObatResponse {
        return apiService?.getObat("Bearer $tokenP")!!
    }

    suspend fun addObat(
        tokenP: String,
        nama : RequestBody?,
        deskripsi : RequestBody?,
        harga :RequestBody?,
        stok:RequestBody?,
        tipePenyakit : RequestBody?,
        photo : MultipartBody.Part?,
        link: RequestBody?) : AddObatResponse {
        Log.d("MedicineStoreRepository", "addObat called with: tokenP = $tokenP, nama = $nama, deskripsi = $deskripsi, stok = $stok, harga = $harga, photo = $photo")

        return withContext(Dispatchers.IO) {
            apiService?.addObat("Bearer $tokenP",nama,deskripsi,harga,stok,tipePenyakit,photo,link)!!
        }

    }

    suspend fun deteleObat(tokenP:String, idObat : String) : HapusObatResponse {
        return apiService?.deleteObat("Bearer $tokenP",idObat)!!
    }

    suspend fun editObat(tokenP: String, idObat: String,desk:RequestBody?,nama:RequestBody?,stok:RequestBody?,harga:RequestBody?,penyakit: RequestBody?,photo:MultipartBody.Part?,link:RequestBody?) : EditObatResponse {
        return apiService?.editObat("Bearer $tokenP",idObat,nama,desk,harga,stok,penyakit,photo,link)!!
    }


}