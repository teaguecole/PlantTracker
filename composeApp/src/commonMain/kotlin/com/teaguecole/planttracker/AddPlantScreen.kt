package com.teaguecole.planttracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap

/**
 * Screen for adding a new plant.
 *
 * KMP note: "State hoisting" is a key Compose pattern. This screen manages its own
 * form state (name, location) internally, but when the user saves, it passes the
 * finished Plant up to the caller via onSave. The parent decides what to do with it.
 *
 * @param onSave Called with the new Plant when the user taps Save
 * @param onCancel Called when the user wants to go back without saving
 */
@Composable
fun AddPlantScreen(
    onSave: (Plant) -> Unit,
    onCancel: () -> Unit
) {
    // mutableStateOf creates observable state — when these change, the UI recomposes.
    // "by" delegates let us use name/location directly instead of name.value/location.value.
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()
    val picker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            imageBytes = byteArrays.firstOrNull()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimens.spacingLg),
        verticalArrangement = Arrangement.spacedBy(AppDimens.spacingSm)
    ) {
        // remember(imageBytes) means: recompute the bitmap only when imageBytes changes.
        // Without the key, it would re-decode the entire image on every recomposition.
        // toImageBitmap() is from Peekaboo — it's an expect/actual function that
        // converts bytes to a platform-appropriate bitmap (Skia on iOS, Android bitmap on Android).
        val imageBitmap = remember(imageBytes) {
            imageBytes?.toImageBitmap()
        }

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Selected plant photo",
                modifier = Modifier.size(AppDimens.imageThumbnail)
            )
        }

        // Show "Add Photo" or "Change Photo" depending on whether one is already picked.
        Button(
            onClick = { picker.launch() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (imageBitmap != null) "Change Photo" else "Add Photo")
        }


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Plant Name")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppDimens.spacingLg))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    // Using a simple counter-based ID for now.
                    // We'll switch to proper IDs when we add a database.
                    onSave(
                        Plant(
                            id = name.hashCode().toString(),
                            name = name.trim(),
                            location = location.trim()
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Plant")
        }

        Spacer(modifier = Modifier.height(AppDimens.spacingSm))

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
