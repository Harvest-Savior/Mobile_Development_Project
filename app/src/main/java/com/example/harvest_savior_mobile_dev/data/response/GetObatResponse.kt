package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class GetObatResponse(

	@field:SerializedName("medicines")
	val medicines: List<MedicinesItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class MedicinesItem(

	@field:SerializedName("penyakit")
	val penyakit: String? = null,

	@field:SerializedName("storeuserId")
	val storeuserId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("linkProduct")
	val linkProduct: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("namaObat")
	val namaObat: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
