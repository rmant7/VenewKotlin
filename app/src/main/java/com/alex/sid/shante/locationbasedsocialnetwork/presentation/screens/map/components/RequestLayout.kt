package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.sid.shante.locationbasedsocialnetwork.R
import com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map.MapViewModel

@Composable
fun RequestLayout(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onKeyboardDone: () -> Unit
) {

    val viewModel: MapViewModel = hiltViewModel()

    Row(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = userInput,
            singleLine = true,
            modifier = modifier.weight(1f),
            onValueChange = onUserInputChange,
            label = { Text(stringResource(R.string.address)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            )
        )
        if (userInput.isNotEmpty()) {
            Icon(
                modifier = modifier
                    .width(40.dp)
                    .clickable { viewModel.clearTextField() },
                imageVector = Icons.Default.Close,
                contentDescription = "delete history item"
            )
        }
    }
}