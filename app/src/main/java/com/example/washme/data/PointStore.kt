package com.example.washme.data

import com.example.washme.data.entities.WashMePoint
import com.example.washme.di.CoroutinesModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointStore
@Inject constructor(
    private val pointDao: PointDao,
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllMapPoints(): List<WashMePoint> = withContext(ioDispatcher) {
        pointDao.getAllMapPoints()
    }

    suspend fun getPointByCoordination(
        latitude: Double, longitude: Double
    ): WashMePoint = withContext(ioDispatcher) {
        pointDao.getPointByCoordination(latitude, longitude)
    }

    suspend fun addPoint(entity: WashMePoint) = withContext(ioDispatcher) {
        pointDao.insert(entity)
    }

    suspend fun addAllPoints(entity: Collection<WashMePoint>) = withContext(ioDispatcher) {
        pointDao.insertAll(entity)
    }

    suspend fun deletePoint(entity: WashMePoint) = withContext(ioDispatcher) {
        pointDao.delete(entity)
    }

    suspend fun updatePoint(entity: WashMePoint) = withContext(ioDispatcher) {
        pointDao.update(entity)
    }
}