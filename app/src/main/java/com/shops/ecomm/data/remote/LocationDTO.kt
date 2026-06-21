package com.shops.ecomm.data.remote

import com.google.gson.annotations.SerializedName

data class LocationDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double

)