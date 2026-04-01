package com.teaguecole.planttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Main screen showing the list of plants.
 *
 * KMP note: This composable is in commonMain, so it runs on both Android and iOS.
 * Compose Multiplatform handles rendering natively on each platform.
 */
@Composable
fun PlantListScreen(plants: List<Plant>, onWaterPlant: (Plant) -> Unit) {
    // LazyColumn = scrollable list that only renders visible items (like RecyclerView)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimens.spacingLg),
        verticalArrangement = Arrangement.spacedBy(AppDimens.spacingMd)
    ) {
        // "items" takes a list and creates a composable for each entry.
        // "key" helps Compose track which items moved/changed for efficient updates.
        items(items = plants, key = { it.id }) { plant ->
            PlantCard(plant, onWater = { onWaterPlant(plant) })
        }
    }
}

/**
 * A card displaying a single plant's info with a water button.
 */
@Composable
fun PlantCard(plant: Plant, onWater: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.elevationSm)
    ) {
        Row(
            modifier = Modifier
                .padding(AppDimens.spacingLg)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacingMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimens.imageThumbnail)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = plant.location,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatLastWatered(plant.lastWateredDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = onWater,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Water")
            }
        }
    }
}

/**
 * Formats a timestamp (epoch millis) into a readable date string.
 * Returns "Never watered" if null.
 */
private fun formatLastWatered(epochMillis: Long?): String {
    if (epochMillis == null) return "Never watered"

    // KMP note: kotlinx-datetime gives us cross-platform date/time handling.
    // On Android you might reach for java.time, but that doesn't exist on iOS.
    // Instant = a point in time. We convert it to a local date for display.
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(epochMillis)
    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return "Watered: ${localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${localDate.dayOfMonth}, ${localDate.year}"
}
