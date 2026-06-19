package com.shops.ecomm.data.mapper

import com.shops.ecomm.data.remote.LocationDTO
import com.shops.ecomm.domain.model.Location


fun LocationDTO.toLocation(): Location {

    return Location(
        id = id,
        name = name,
        description = description
    )

}