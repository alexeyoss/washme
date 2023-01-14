package com.example.washme.utils

import com.example.washme.data.entities.UserLocation

sealed interface Locations {
    companion object {
        private const val MOSCOW_LATITUDE = 55.751999
        private const val MOSCOW_LONGITUDE = 37.617734

        @JvmStatic
        val DEFAULT_POINT_COORDINATION =
            UserLocation(latitude = MOSCOW_LATITUDE, longitude = MOSCOW_LONGITUDE)
    }
}