package com.alex.sid.shante.locationbasedsocialnetwork.domain.repositories

import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Place

interface MapRepository {

    suspend fun getPlaceByRequest(request: String) : Place

}