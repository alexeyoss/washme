package com.example.washme.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.washme.data.entities.UserLocation
import java.util.*

@Dao
abstract class LocationDao {

    private val calendar: Calendar by lazy {
        Calendar.getInstance()
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(userLocation: UserLocation): Long

    suspend fun insertWithTimesTamp(userLocation: UserLocation): Long {
        return insert(userLocation.apply {
            this.createdAt = calendar.time.time
        })
    }

}