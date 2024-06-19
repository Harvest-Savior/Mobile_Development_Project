package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class DeteksiFarmerResponse(

	@field:SerializedName("prediction")
	val prediction: Prediction? = null
)

data class Prediction(

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
