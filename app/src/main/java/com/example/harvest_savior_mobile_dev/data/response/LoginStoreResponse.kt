package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class LoginStoreResponse(

	@field:SerializedName("data")
	val data: DataLoginStore? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataLoginStore(

	@field:SerializedName("namaToko")
	val namaToko: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)