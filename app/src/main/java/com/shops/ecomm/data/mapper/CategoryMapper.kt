package com.shops.ecomm.data.mapper

import com.shops.ecomm.data.remote.CategoryDTO
import com.shops.ecomm.domain.model.Category

fun CategoryDTO.toCategory(): Category {

    return Category(
        id = id,
        name = name,
        slug = slug,
        image = image
    )

}