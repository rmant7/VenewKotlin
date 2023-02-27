package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map

import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Feature

data class MapState(
    val coordinatesOfCenter: Coordinates = Coordinates(15.8700, 100.9925),
    val userInput: String = "",
    val hintsForRequest: List<Feature> = emptyList(),
    val isPlaceFound: Boolean = false
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)