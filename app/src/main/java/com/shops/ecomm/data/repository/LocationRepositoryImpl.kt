package com.shops.ecomm.data.repository

import com.shops.ecomm.data.mapper.toLocation
import com.shops.ecomm.data.remote.LocationApi
import com.shops.ecomm.domain.model.Location
import com.shops.ecomm.domain.repository.LocationRepository
import jakarta.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi
): LocationRepository {


    override suspend fun getLocations(): List<Location> {

        return api.getLocations()
            .map {
                it.toLocation()
            }

    }

}