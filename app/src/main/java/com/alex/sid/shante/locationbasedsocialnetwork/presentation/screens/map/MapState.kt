package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map

import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Feature

data class MapState(
    val focusCoordinates: Coordinates = STARTING_COORDINATES,
    val currentPosition: Coordinates = STARTING_COORDINATES,
    val zoomLevel: Double = STARTING_ZOOM_LEVEL,
    val userInput: String = "",
    val hintsForRequest: List<Feature> = emptyList(),
    val isPlaceFound: Boolean = false
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

val STARTING_COORDINATES = Coordinates(0.0 , 0.0)
const val STARTING_ZOOM_LEVEL = 20.2
