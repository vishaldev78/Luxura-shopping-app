package com.shops.ecomm.data.remote

import retrofit2.http.GET


interface CategoryApi {


    @GET("categories")
    suspend fun getCategories()
            : List<CategoryDTO>


}