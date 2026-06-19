package com.shops.ecomm.domain.repository

import com.shops.ecomm.domain.model.Location


interface LocationRepository {

    suspend fun getLocations(): List<Location>

}