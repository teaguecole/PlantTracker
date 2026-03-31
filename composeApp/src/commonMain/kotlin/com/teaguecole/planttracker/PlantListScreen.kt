package com.teaguecole.planttracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main screen showing the list of plants.
 *
 * KMP note: This composable is in commonMain, so it runs on both Android and iOS.
 * Compose Multiplatform handles rendering natively on each platform.
 */
@Composable
fun PlantListScreen(plants: List<Plant>) {
    // LazyColumn = scrollable list that only renders visible items (like RecyclerView)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // "items" takes a list and creates a composable for each entry.
        // "key" helps Compose track which items moved/changed for efficient updates.
        items(items = plants, key = { it.id }) { plant ->
            PlantCard(plant)
        }
    }
}

/**
 * A card displaying a single plant's info.
 */
@Composable
fun PlantCard(plant: Plant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium)
                Text(
                    text = plant.location,
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
