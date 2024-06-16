package com.example.harvest_savior_mobile_dev.repository

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

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

    suspend fun addObat(tokenP: String, nama : String, deskripsi : String, stok:Int, harga : Int, photo : MultipartBody.Part?) : AddObatResponse {
        return apiService.addObat("Bearer $tokenP",nama,deskripsi,stok,harga,photo)

    }

    suspend fun deteleObat(tokenP:String, idObat : String) : HapusObatResponse {
        return apiService.deleteObat(tokenP,idObat)
    }

    suspend fun editObat(tokenP: String, idObat: String) : EditObatResponse {
        return apiService.editObat(tokenP,idObat)
    }

}