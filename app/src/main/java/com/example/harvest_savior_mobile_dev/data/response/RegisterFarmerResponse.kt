package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class RegisterFarmerResponse(

	@field:SerializedName("data")
	val data: DataRegisterFarmer? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataRegisterFarmer(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
