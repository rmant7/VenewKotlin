package com.alex.sid.shante.locationbasedsocialnetwork.domain.models

data class Place(
    val features: List<Feature>,
    val licence: String,
    val type: String
)

data class Feature(
    val bbox: List<Double>,
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val category: String,
    val display_name: String,
    val importance: Double,
    val osm_id: Long,
    val osm_type: String,
    val place_id: Int,
    val place_rank: Int,
    val type: String
)
