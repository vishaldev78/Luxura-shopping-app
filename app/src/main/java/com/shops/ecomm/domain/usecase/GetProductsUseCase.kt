package com.shops.ecomm.domain.usecase

import com.shops.ecomm.domain.repository.ProductRepository
import javax.inject.Inject


class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.getProducts()
}