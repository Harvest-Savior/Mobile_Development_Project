package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class LoginFarmerResponse(

	@field:SerializedName("data")
	val data: DataLoginFarmer? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataLoginFarmer(

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
