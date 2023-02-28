package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.sid.shante.locationbasedsocialnetwork.R
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components.AddressHint
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components.Map
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components.RequestLayout
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MapViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Map(
            focusCoordinates = state.focusCoordinates,
            currentPositionCoordinates = state.currentPosition
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            RequestLayout(
                userInput = state.userInput,
                onUserInputChange = { input ->
                    viewModel.updateUserInput(input)
                    viewModel.getHintsByRequest(input)
                },
                onKeyboardDone = {
                    viewModel.updateUserInput(state.userInput)
                    viewModel.getHintsByRequest(state.userInput)
                }
            )
            if (state.userInput.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.hintsForRequest) { it ->
                        AddressHint(address = it) {
                            viewModel.scrollToCoordinates(
                                Coordinates(
                                    latitude = it.geometry.coordinates[1],
                                    longitude = it.geometry.coordinates[0]
                                )
                            )
                        }
                    }
                }
            }
        }
        if (state.isPlaceFound) {
            Box(
                modifier = modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_map_point),
                    contentDescription = "map point"
                )
            }
        }
    }
}

fun addMarker(view: MapView, coordinates: Pair<Int, Int>, context: Context) {
    val marker = Marker(view).apply {
        position = GeoPoint(coordinates.first, coordinates.second)
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        icon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_map_point
        )
        setPanToView(true)
        infoWindow = MyMarkerInfoWindow(R.layout.marker_info, view).apply {
//            title = "Hello"
//            subDescription = "asdasd"
        }
        setOnMarkerClickListener { marker, mapView ->
            marker.showInfoWindow()
            true
        }
    }
    view.overlays.add(marker)
    view.invalidate()
}


@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}