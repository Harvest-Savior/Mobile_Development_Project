package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class HapusObatResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
