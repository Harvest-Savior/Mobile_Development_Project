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
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MedicineStoreRepository(private val apiService: ApiService, private val preference: LoginStorePreference ) {
    suspend fun login(email: String, password : String) : LoginStoreResponse {
        return apiService.postLoginStore(email,password)
    }

    suspend fun register(namaToko : String, alamat : String, email: String, noHp : Int, password: String ) : RegisterStoreResponse {
        return apiService.postRegisterStore(namaToko,alamat,email,noHp,password)
    }

    suspend fun getObat(tokenP : String?) : GetObatResponse {
        return apiService.getObat("Bearer $tokenP")
    }

    suspend fun addObat(
        tokenP: String,
        nama : RequestBody?,
        deskripsi : RequestBody?,
        stok:RequestBody?,
        harga :RequestBody?,
        photo : MultipartBody.Part?) : AddObatResponse {
        Log.d("MedicineStoreRepository", "addObat called with: tokenP = $tokenP, nama = $nama, deskripsi = $deskripsi, stok = $stok, harga = $harga, photo = $photo")

        return withContext(Dispatchers.IO) {
        apiService.addObat("Bearer $tokenP",nama,deskripsi,stok,harga,photo)
        }

    }

    suspend fun deteleObat(tokenP:String, idObat : String) : HapusObatResponse {
        return apiService.deleteObat("Bearer $tokenP",idObat)
    }

    suspend fun editObat(tokenP: String, idObat: String,desk:RequestBody?,nama:RequestBody?,stok:Int?,harga:Int?,photo:RequestBody?) : EditObatResponse {
        return apiService.editObat("Bearer $tokenP",idObat,desk,nama,stok,harga,photo)
    }

}