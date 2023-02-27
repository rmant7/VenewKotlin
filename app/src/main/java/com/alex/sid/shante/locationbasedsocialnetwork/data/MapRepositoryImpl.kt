package com.alex.sid.shante.locationbasedsocialnetwork.data

import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Place
import com.alex.sid.shante.locationbasedsocialnetwork.domain.repositories.MapRepository

class MapRepositoryImpl(
    private val api: NominatimSearchApi
) : MapRepository {

    override suspend fun getPlaceByRequest(request: String): Place {
        return api.getPlaceByRequest(request, format = "geojson")
    }

}