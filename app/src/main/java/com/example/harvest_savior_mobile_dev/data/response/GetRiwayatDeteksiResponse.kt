package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class GetRiwayatDeteksiResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null
)

data class PredictionsItem(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("medicines")
	val medicines: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
