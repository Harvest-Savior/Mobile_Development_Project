package com.example.harvest_savior_mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class DataObatResponse(
    @field:SerializedName("listStory")
    val listStory: List<ObatListItem>? ,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ObatListItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("harga")
    val harga: Int? = null,


)
