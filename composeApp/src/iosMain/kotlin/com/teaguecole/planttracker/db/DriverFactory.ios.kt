package com.teaguecole.planttracker.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

/**
 * iOS "actual" implementation.
 *
 * Simpler than Android — iOS doesn't need a Context. The NativeSqliteDriver
 * uses SQLite directly through Kotlin/Native's C interop and stores the
 * database file in the app's default documents directory.
 */
actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(PlantDatabase.Schema, "plant.db")
}
