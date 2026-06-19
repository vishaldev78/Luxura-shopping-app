package com.shops.ecomm.data.remote


import retrofit2.http.GET


interface LocationApi {


    @GET("locations")
    suspend fun getLocations()
            : List<LocationDTO>


}