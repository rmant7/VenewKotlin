package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map

import android.content.Context
import android.widget.Toast
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
            focusCoordinates = STARTING_COORDINATES,
            currentPosition = STARTING_COORDINATES,
            userInput = "",
            isPlaceFound = false
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

    fun scrollToCoordinates(coordinates: Coordinates) {
        _state.update {
            if (coordinates !== STARTING_COORDINATES) {
                it.copy(
                    focusCoordinates = coordinates,
                    isPlaceFound = true,
                    hintsForRequest = emptyList()
                )
            } else it
        }
    }

    fun setCurrentPosition(coordinates: Coordinates) {
        _state.update {
            if (coordinates !== STARTING_COORDINATES) {
                it.copy(
                    currentPosition = coordinates,
                    hintsForRequest = emptyList()
                )
            } else it
        }
    }

    fun getHintsByRequest(input: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val place = repository.getPlaceByRequest(request = input)
                _state.update { state -> state.copy(hintsForRequest = place.features) }
            } catch (error: Throwable) {
                println(error.message)
            }
        }
    }

    fun changeZoomLevel(zoomValue: Double) {
        _state.update {
            it.copy(zoomLevel = zoomValue)
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}