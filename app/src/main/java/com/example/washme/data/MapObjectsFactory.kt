package com.example.washme.data

import com.example.washme.utils.ConstHolder.DEFAULT_MAX_LATITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MAX_LONGITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MIN_LATITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MIN_LONGITUDE
import com.yandex.mapkit.geometry.Point
import timber.log.Timber
import kotlin.random.Random

/** [MapObjectsFactory] randomly provided the fake car wash points on the map */

class MapObjectsFactory {
    fun generateRandomPoints(amount: Int): HashSet<Point> {
        return hashSetOf<Point>().apply {
            repeat(amount) {
                add(Point().factory())
            }
        }
    }
}

fun Point.factory(): Point = Point(getRandomLatitude(), getRandomLongitude())

private fun getRandomLatitude(
    min: Float = DEFAULT_MIN_LATITUDE, max: Float = DEFAULT_MAX_LATITUDE
): Double {
    require(min < max) { Timber.i("Invalid range of LATITUDE [$min, $max]") }
    return min + Random.nextDouble() * (max - min)
}

private fun getRandomLongitude(
    min: Float = DEFAULT_MIN_LONGITUDE, max: Float = DEFAULT_MAX_LONGITUDE
): Double {
    require(min < max) { Timber.i("Invalid range of LONGITUDE $min, $max]") }
    return min + Random.nextDouble() * (max - min)
}

