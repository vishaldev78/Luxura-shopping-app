package com.shops.ecomm.domain.repository

import com.shops.ecomm.domain.model.Category


interface CategoryRepository {

    suspend fun getCategories(): List<Category>

}