package com.shops.ecomm.domain.repository

import com.shops.ecomm.domain.model.Product


interface ProductRepository {


    // get products
    suspend fun getProducts():
            List<Product>

    // for searching product by query
    suspend fun searchProducts(
        query:String
    ): List<Product>

    suspend fun createPaymentIntent(amount: Int): String?



}