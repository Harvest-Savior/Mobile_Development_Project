package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class EditObatResponse(

	@field:SerializedName("data")
	val data: DataEditObat? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataEditObat(

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("namaObat")
	val namaObat: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("user")
	val user: String? = null
)
