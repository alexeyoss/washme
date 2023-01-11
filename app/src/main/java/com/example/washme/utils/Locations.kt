package com.example.washme.utils

import com.yandex.mapkit.geometry.Point

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