package com.example.washme.domain.repository

import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.WashMePoint

interface PointRepository {

    suspend fun generateRandomPoints(amount: Int, location: UserLocation): List<WashMePoint>
    suspend fun savePointIntoDb(entities: List<WashMePoint>)
    suspend fun getAllMapPointsFromDb(): List<WashMePoint>

}