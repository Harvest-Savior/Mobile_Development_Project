package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class DeteksiGetTokenResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null
)
