package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class RegisterFarmerResponse(

	@field:SerializedName("data")
	val data: DataDaftarFarmer? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataDaftarFarmer(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
