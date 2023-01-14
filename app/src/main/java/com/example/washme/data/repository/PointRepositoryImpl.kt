package com.example.washme.data.repository

import com.example.washme.data.PointStore
import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.WashMePoint
import com.example.washme.data.fake_sources.MapObjectsFactory
import com.example.washme.domain.repository.PointRepository
import javax.inject.Inject

class PointRepositoryImpl
@Inject constructor(
    private val pointStore: PointStore,
    private val mapObjectsFactory: MapObjectsFactory
) : PointRepository {

    override suspend fun generateRandomPoints(
        amount: Int,
        location: UserLocation
    ): List<WashMePoint> {
        return mapObjectsFactory.generateRandomPoints(amount, location)
    }

    override suspend fun savePointIntoDb(entities: List<WashMePoint>) {
        pointStore.addAllPoints(entities)
    }

    override suspend fun getAllMapPointsFromDb(): List<WashMePoint> {
        return pointStore.getAllMapPoints()
    }


}