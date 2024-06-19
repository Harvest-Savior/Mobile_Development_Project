package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class RegisterStoreResponse(

	@field:SerializedName("data")
	val data: DataRegisterStore? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataRegisterStore(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("namaToko")
	val namaToko: String? = null,

	@field:SerializedName("noHp")
	val noHp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
