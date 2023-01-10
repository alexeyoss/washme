package com.example.washme.utils

import com.yandex.mapkit.geometry.Point

object ConstHolder {
    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    /** Consts for fake points generator */
    const val DEFAULT_AMOUNT_OF_POINTS = 10
    const val DEFAULT_MAX_LATITUDE = 50f
    const val DEFAULT_MIN_LATITUDE = 0f
    const val DEFAULT_MAX_LONGITUDE = 50f
    const val DEFAULT_MIN_LONGITUDE = 0f

}

sealed interface Locations {
    object DefaultCoordination {
        private val point: Point = DEFAULT_POINT_COORDINATION
        fun getPoint() = point
    }

    data class UserCoordination(private val point: Point) {
        fun getPoint() = point
    }

    companion object {
        private val DEFAULT_POINT_COORDINATION = Point(55.751999, 37.617734)
    }
}