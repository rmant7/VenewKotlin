package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.sid.shante.locationbasedsocialnetwork.di.IoDispatcher
import com.alex.sid.shante.locationbasedsocialnetwork.domain.repositories.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    init {
        _state.value = MapState(
            coordinatesOfCenter = Coordinates(latitude = 15.8700, longitude = 100.9925),
            userInput = ""
        )
    }

    fun updateUserInput(input: String) {
        _state.update { state ->
            state.copy(userInput = input)
        }
    }

    fun clearTextField() {
        _state.update { state ->
            state.copy(userInput = "", isPlaceFound = false)
        }
    }

    fun findPlace(coordinates: Coordinates) {
        _state.update {
            it.copy(coordinatesOfCenter = coordinates, isPlaceFound = true, hintsForRequest = emptyList())
        }
    }

    fun getHintsByRequest(input: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val place = repository.getPlaceByRequest(request = input)
                println(place)
                _state.update { state -> state.copy(hintsForRequest = place.features) }
            } catch (error: Throwable) {
                println(error.message)
            }
        }
    }

}