package com.shops.ecomm.data.repository

import com.shops.ecomm.data.mapper.toProduct
import com.shops.ecomm.data.remote.ProductApi
import com.shops.ecomm.domain.model.Product
import com.shops.ecomm.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return try {
            api.getProducts().map { it.toProduct() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchProducts(query: String): List<Product> {
        return try {
            api.searchProducts(query).map { it.toProduct() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun createPaymentIntent(amount: Int): String? {
        return try {
            val response = api.createPaymentIntent(
                auth = "Bearer sk_test_51TI6HYRtLqieFrm3Bov1vI1Y5kUv9o5B0oY666888222333444555", // replace with actual secret key for temporary testing
                amount = amount
            )
            response["client_secret"] as? String
        } catch (e: Exception) {
            null
        }
    }
}
