package com.example.washme.domain.repository

import com.example.washme.data.DataResponseState
import com.example.washme.data.entities.UserLocation

interface LocationRepository {
    suspend fun saveLastUserLocation(userLocation: UserLocation): DataResponseState<Long>
}