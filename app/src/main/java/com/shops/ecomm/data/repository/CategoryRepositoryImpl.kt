package com.shops.ecomm.data.repository


import com.shops.ecomm.data.mapper.toCategory
import com.shops.ecomm.data.remote.CategoryApi
import com.shops.ecomm.domain.model.Category
import com.shops.ecomm.domain.repository.CategoryRepository
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi
): CategoryRepository {


    override suspend fun getCategories(): List<Category> {

        return api.getCategories()
            .map {
                it.toCategory()
            }

    }

}