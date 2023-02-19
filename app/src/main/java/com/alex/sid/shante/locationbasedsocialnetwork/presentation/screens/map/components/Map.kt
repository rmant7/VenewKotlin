package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.Coordinates
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun Map(
    focusCoordinates: Coordinates
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setBuiltInZoomControls(true)
                setMultiTouchControls(true)
                controller.apply {
                    setZoom(10)
                    setCenter(GeoPoint(focusCoordinates.latitude, focusCoordinates.longitude))
                }
                Configuration.getInstance()
                    .load(context, PreferenceManager.getDefaultSharedPreferences(context))
                this.invalidate()
            }
        },
        update = { view ->
//            coordinates.forEach { coordinates ->
//                addMarker(view, coordinates, context)
//            }
            view.controller.setCenter(GeoPoint(focusCoordinates.latitude, focusCoordinates.longitude))
        }
    )
}