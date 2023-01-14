package com.example.washme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.washme.data.entities.Schedule
import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.WashMePoint
import com.example.washme.utils.ConstHolder

/**
 * The [RoomDatabase] we use in this app.
 */

@Database(
    entities = [
        WashMePoint::class,
        Schedule::class,
        UserLocation::class
    ],
    version = ConstHolder.WASHME_DB_VERSION,
    exportSchema = false
)
abstract class WashMeDB : RoomDatabase() {
    abstract fun pointDao(): PointDao
    abstract fun locationDao(): LocationDao
}