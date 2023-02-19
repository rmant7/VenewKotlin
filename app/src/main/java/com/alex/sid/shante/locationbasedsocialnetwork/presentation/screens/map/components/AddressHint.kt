package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alex.sid.shante.locationbasedsocialnetwork.domain.models.Feature

@Composable
fun AddressHint(
    modifier: Modifier = Modifier,
    address: Feature,
    onAddressHintClicked: (Feature) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 1.dp)
            .background(Color.White.copy(alpha = 0.6f))
            .clickable {
                onAddressHintClicked(address)
            }
    )
    {
        Text(text = address.properties.display_name)
    }
}