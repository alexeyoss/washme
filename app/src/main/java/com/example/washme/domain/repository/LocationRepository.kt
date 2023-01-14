package com.example.washme.domain.repository

import com.example.washme.data.DataResponseState
import com.example.washme.data.entities.UserLocation
import com.yandex.mapkit.geometry.Point

interface LocationRepository {
    suspend fun saveLastUserLocation(userLocation: UserLocation): DataResponseState<Long>
}