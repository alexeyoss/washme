package com.example.washme.data

import com.example.washme.data.entities.UserLocation
import com.example.washme.di.CoroutinesModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationStore
@Inject
constructor(
    private val locationDao: LocationDao,
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun saveUserLocation(userLocation: UserLocation) = withContext(ioDispatcher) {
        locationDao.insertWithTimesTamp(userLocation)
    }

}