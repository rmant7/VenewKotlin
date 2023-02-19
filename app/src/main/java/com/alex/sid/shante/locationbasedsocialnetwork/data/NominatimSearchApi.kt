package com.alex.sid.shante.locationbasedsocialnetwork.data

import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Place
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimSearchApi {

    @GET("search")
    suspend fun getPlaceByRequest(@Query("q") request: String, @Query("format") format: String): Place
}