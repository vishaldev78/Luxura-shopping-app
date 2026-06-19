package com.shops.ecomm.domain.usecase

import com.shops.ecomm.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke() = repository.getLocations()
}
