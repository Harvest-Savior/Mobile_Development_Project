package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class DeteksiResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("medication_recommendations")
	val medicationRecommendations: List<MedicationRecommendationsItem?>? = null
)

data class Result(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("solution")
	val solution: String? = null,

	@field:SerializedName("cause")
	val cause: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("prevention_method")
	val preventionMethod: String? = null,

	@field:SerializedName("plant_name")
	val plantName: String? = null
)

data class MedicationRecommendationsItem(

	@field:SerializedName("linkProduct")
	val linkProduct: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("namaObat")
	val namaObat: String? = null,

	@field:SerializedName("Toko")
	val toko: Toko? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)

data class Toko(

	@field:SerializedName("nama Toko")
	val namaToko: String? = null,

	@field:SerializedName("noHp")
	val noHp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
