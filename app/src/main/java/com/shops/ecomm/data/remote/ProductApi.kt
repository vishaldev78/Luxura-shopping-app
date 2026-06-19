package com.shops.ecomm.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApi {

    @POST("https://api.stripe.com/v1/payment_intents")
    suspend fun createPaymentIntent(
        @Header("Authorization") auth: String,
        @Query("amount") amount: Int,
        @Query("currency") currency: String = "inr",
        @Query("payment_method_types[]") paymentMethodType: String = "card"
    ): Map<String, Any>

    // product
    @GET("products")
    suspend fun getProducts(): List<ProductDTO>


    // search
    @GET("products")
    suspend fun searchProducts(
        @Query("title") title: String
    ): List<ProductDTO>


    // filter price
    @GET("products")
    suspend fun filterByPrice(
        @Query("price") price: Int
    ): List<ProductDTO>


    // price range
    @GET("products")
    suspend fun filterPriceRange(
        @Query("price_min") min: Int, @Query("price_max") max: Int
    ): List<ProductDTO>


    // category id
    @GET("products")
    suspend fun getByCategoryId(
        @Query("categoryId") id: Int
    ): List<ProductDTO>


    // category slug
    @GET("products")
    suspend fun getByCategorySlug(
        @Query("categorySlug") slug: String
    ): List<ProductDTO>

}