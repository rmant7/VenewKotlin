package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.Coordinates
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.MapViewModel
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.STARTING_COORDINATES
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.STARTING_ZOOM_LEVEL
import com.google.android.gms.location.LocationServices
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("MissingPermission")
@Composable
fun Map(
    focusCoordinates: Coordinates,
    currentPositionCoordinates: Coordinates,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: MapViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    requestPermission(context)
    val task = fusedLocationProviderClient.lastLocation
    if (hasPermissions(context = context)) {
        task.addOnSuccessListener {
            if (it != null) {
                viewModel.setCurrentPosition(Coordinates(it.latitude, it.longitude))
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        var focused: IGeoPoint
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->

                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setBuiltInZoomControls(true)
                    setMultiTouchControls(true)
                    controller.apply {
                        setZoom(20.2)
                        setCenter(
                            GeoPoint(
                                viewModel.state.value.currentPosition.latitude,
                                viewModel.state.value.currentPosition.longitude
                            )
                        )
                    }
                    this.addMapListener(object : MapListener {
                        override fun onScroll(event: ScrollEvent?): Boolean {
                            focused = mapCenter
                            viewModel.scrollToCoordinates(
                                Coordinates(
                                    focused.latitude,
                                    focused.longitude
                                )
                            )
                            viewModel.setCurrentPosition(
                                Coordinates(
                                    focused.latitude,
                                    focused.longitude
                                )
                            )
                            return true
                        }

                        override fun onZoom(event: ZoomEvent?): Boolean {
                            val currentZoomValue = this@apply.zoomLevelDouble
                            viewModel.changeZoomLevel(currentZoomValue)
                            return true
                        }
                    })
                    Configuration.getInstance()
                        .load(context, PreferenceManager.getDefaultSharedPreferences(context))
                    invalidate()
                }
            },
            update = { view ->
                if (currentPositionCoordinates != STARTING_COORDINATES
                    && focusCoordinates == STARTING_COORDINATES
                ) {
                    view.controller.apply {
                        setCenter(
                            GeoPoint(
                                currentPositionCoordinates.latitude,
                                currentPositionCoordinates.longitude
                            )
                        )
                        setZoom(state.zoomLevel)
                    }
                } else {
                    view.controller.apply {
                        setCenter(
                            GeoPoint(
                                focusCoordinates.latitude,
                                focusCoordinates.longitude
                            )
                        )
                        setZoom(state.zoomLevel)
                    }
                }
            }
        )
        FloatingActionButton(
            onClick = {
                if (hasPermissions(context = context)) {
                    viewModel.scrollToCoordinates(
                        Coordinates(
                            currentPositionCoordinates.latitude,
                            currentPositionCoordinates.longitude
                        )
                    )
                    viewModel.changeZoomLevel(STARTING_ZOOM_LEVEL)
                }
            },
            modifier = modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 40.dp, bottom = 60.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Navigation"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.Q)
fun requestPermission(context: Context) {
    if (!hasPermissions(context = context)) {
        requestPermissions(
            context as Activity,
            PERMISSIONS_REQUIRED,
            PERMISSIONS_REQUEST_CODE
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_BACKGROUND_LOCATION
)
const val PERMISSIONS_REQUEST_CODE = 10