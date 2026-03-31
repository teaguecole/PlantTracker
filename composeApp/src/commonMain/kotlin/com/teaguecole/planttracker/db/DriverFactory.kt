package com.teaguecole.planttracker.db

import app.cash.sqldelight.db.SqlDriver

/**
 * expect/actual in action!
 *
 * "expect" declares a function signature in common code without an implementation.
 * Each platform provides an "actual" implementation. The compiler ensures every
 * platform has one — if you forget, it won't build.
 *
 * Why do we need this? Android creates SQLite databases through a Context-aware API,
 * while iOS uses a different native SQLite driver. The common code doesn't need to
 * know these details — it just calls createDriver() and gets back a SqlDriver.
 */
expect fun createDriver(): SqlDriver
