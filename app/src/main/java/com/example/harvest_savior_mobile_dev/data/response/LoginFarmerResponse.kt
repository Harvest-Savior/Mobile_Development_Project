package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class LoginFarmerResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
