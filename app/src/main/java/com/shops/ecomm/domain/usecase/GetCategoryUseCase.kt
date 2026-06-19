package com.shops.ecomm.domain.usecase

import com.shops.ecomm.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke() = repository.getCategories()
}
