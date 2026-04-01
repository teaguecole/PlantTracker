package com.teaguecole.planttracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.teaguecole.planttracker.db.PlantDatabase
import com.teaguecole.planttracker.db.PlantRepository
import com.teaguecole.planttracker.db.createDriver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        // Create the database and repository once.
        // remember ensures these survive recomposition — without it, we'd create
        // a new database connection every time the UI updates.
        val repository = remember {
            val driver = createDriver()
            val database = PlantDatabase(driver)
            PlantRepository(database)
        }

        // Load plants from the database into a Compose-observable list.
        // toMutableStateList() means adds/removes will trigger UI updates.
        val plants = remember { repository.getAllPlants().toMutableStateList() }

        var showAddScreen by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(if (showAddScreen) "Add Plant" else "PlantTracker")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            floatingActionButton = {
                if (!showAddScreen) {
                    FloatingActionButton(onClick = { showAddScreen = true }) {
                        Text("+", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                if (showAddScreen) {
                    AddPlantScreen(
                        onSave = { newPlant ->
                            repository.insertPlant(newPlant)  // persist to database
                            plants.add(newPlant)              // update UI
                            showAddScreen = false
                        },
                        onCancel = { showAddScreen = false }
                    )
                } else {
                    PlantListScreen(
                        plants = plants,
                        onWaterPlant = { plant ->
                            repository.markAsWatered(plant.id)
                            // Update the in-memory list so the UI reflects the change.
                            // We find the plant's index and replace it with an updated copy.
                            val index = plants.indexOfFirst { it.id == plant.id }
                            if (index != -1) {
                                val now = kotlin.time.Clock.System.now().toEpochMilliseconds()
                                plants[index] = plant.copy(lastWateredDate = now)
                            }
                        }
                    )
                }
            }
        }
    }
}