package com.example.washme.domain.repository

import com.example.washme.data.entities.WashMePoint

interface PointRepository {

    suspend fun generateRandomPoints(amount: Int): List<WashMePoint>
    suspend fun savePointIntoDb(entities: List<WashMePoint>)
    suspend fun getAllMapPointsFromDb(): List<WashMePoint>

}