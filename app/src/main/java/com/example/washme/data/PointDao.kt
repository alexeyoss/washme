package com.example.washme.data

import androidx.room.*
import com.example.washme.data.entities.WashMePoint
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PointDao {

    @Query("SELECT * FROM points")
    abstract suspend fun getAllMapPoints(): List<WashMePoint>

    @Query("SELECT * FROM points WHERE latitude =:latitude & longitude = :longitude ")
    abstract suspend fun getPointByCoordination(latitude: Double, longitude: Double): WashMePoint
    /** Some standard CRUD operations */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: WashMePoint): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<WashMePoint>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: WashMePoint)

    @Delete
    abstract suspend fun delete(entity: WashMePoint): Int

}
