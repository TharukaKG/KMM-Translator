package com.tharuka.traslator_kmm.translate.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create():SqlDriver
}