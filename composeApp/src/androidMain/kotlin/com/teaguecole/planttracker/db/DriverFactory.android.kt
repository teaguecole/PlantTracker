package com.teaguecole.planttracker.db

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/**
 * Android "actual" implementation.
 *
 * Android's SQLite needs a Context to know where to store the database file.
 * We store a reference to the Application context (which is safe — it lives
 * as long as the process) and use it when creating the driver.
 */

lateinit var appContext: Application

actual fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(PlantDatabase.Schema, appContext, "plant.db")
}
