package com.teaguecole.planttracker

import androidx.compose.ui.unit.dp

/**
 * Central place for dimension tokens used across the app.
 *
 * KMP note: This is just a plain Kotlin object — no platform-specific code needed.
 * It lives in commonMain so every screen can reference it. This is the same "design
 * tokens" pattern that larger design systems use (your work's FooDimens follows the
 * same idea). The key benefit: if you decide "medium spacing should be 20dp not 16dp",
 * you change ONE line here instead of hunting through every file.
 *
 * Add new tokens here as the app grows — icon sizes, image dimensions, etc.
 */
object AppDimens {

    // Spacing scale — a consistent set of values for padding, gaps, and margins.
    // Using a rough 4dp base grid (4, 8, 12, 16, 24, 32) keeps things visually even.
    val spacingXs = 4.dp
    val spacingSm = 8.dp
    val spacingMd = 12.dp
    val spacingLg = 16.dp
    val spacingXl = 24.dp
    val spacingXxl = 32.dp

    // Elevation
    val elevationSm = 2.dp

    //Image Placeholder
    val imageThumbnail = 64.dp
}