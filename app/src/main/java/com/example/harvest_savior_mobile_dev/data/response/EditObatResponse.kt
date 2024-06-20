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

	@field:SerializedName("penyakit")
	val penyakit: String? = null,

	@field:SerializedName("linkProduct")
	val linkProduct: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("gambarName")
	val gambarName: String? = null,

	@field:SerializedName("namaObat")
	val namaObat: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("stok")
	val stok: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
