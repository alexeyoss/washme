package com.example.washme.data

import com.example.washme.data.entities.WashMePoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointStore
@Inject
constructor(
    private val pointDao: PointDao
) {
    suspend fun getAllMapPoints(): List<WashMePoint> {
        return pointDao.getAllMapPoints()
    }

    suspend fun getPointByCoordination(latitude: Double, longitude: Double): WashMePoint {
        return pointDao.getPointByCoordination(latitude, longitude)
    }

    suspend fun addPoint(entity: WashMePoint) {
        pointDao.insert(entity)
    }

    suspend fun addAllPoints(entity: Collection<WashMePoint>) {
        pointDao.insertAll(entity)
    }

    suspend fun deletePoint(entity: WashMePoint) {
        pointDao.delete(entity)
    }

    suspend fun updatePoint(entity: WashMePoint) {
        pointDao.update(entity)
    }
}