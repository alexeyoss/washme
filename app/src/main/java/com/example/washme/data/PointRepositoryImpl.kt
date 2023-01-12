package com.example.washme.data

import com.example.washme.data.entities.WashMePoint
import com.example.washme.data.fake_sources.MapObjectsFactory
import com.example.washme.domain.PointRepository
import com.example.washme.utils.safeCall
import javax.inject.Inject

class PointRepositoryImpl
@Inject constructor(
    private val pointStore: PointStore,
    private val mapObjectsFactory: MapObjectsFactory
) : PointRepository {

    override suspend fun generateRandomPoints(amount: Int): List<WashMePoint> {
        return mapObjectsFactory.generateRandomPoints(amount) // TODO switch to remote point provider

    }

    override suspend fun savePointIntoDb(entities: List<WashMePoint>) {
        safeCall {
            pointStore.addAllPoints(entities)
        }
    }

    override suspend fun getAllMapPointsFromDb(): List<WashMePoint> {
        return pointStore.getAllMapPoints()
    }


}