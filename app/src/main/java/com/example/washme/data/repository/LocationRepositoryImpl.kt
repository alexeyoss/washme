package com.example.washme.data.repository

import com.example.washme.data.LocationStore
import com.example.washme.data.entities.UserLocation
import com.example.washme.domain.repository.LocationRepository
import com.example.washme.utils.safeCall
import javax.inject.Inject

class LocationRepositoryImpl
@Inject
constructor(
    private val locationStore: LocationStore
) : LocationRepository {
    override suspend fun saveLastUserLocation(userLocation: UserLocation) {
        safeCall {
            locationStore.saveUserLocation(userLocation)
        }
    }

}