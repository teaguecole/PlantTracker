package com.teaguecole.planttracker

/**
 * Represents a plant the user is tracking.
 */
data class Plant(
    val id: String,
    val name: String,
    val location: String,
    val photoUri: String? = null,
    val lastWateredDate: Long? = null,
    val wateringInterval: Int = 7,
)
