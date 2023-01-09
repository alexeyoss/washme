package com.example.washme.data

import com.yandex.mapkit.geometry.Point
import timber.log.Timber
import kotlin.random.Random

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


private const val DEFAULT_MAX_LATITUDE = 50f
private const val DEFAULT_MIN_LATITUDE = 0f
private const val DEFAULT_MAX_LONGITUDE = 50f
private const val DEFAULT_MIN_LONGITUDE = 0f


private fun getRandomLatitude(
    min: Float = DEFAULT_MIN_LATITUDE, max: Float = DEFAULT_MAX_LATITUDE
): Double {
    require(min < max) { Timber.i("Invalid range OF LATITUDE [$min, $max]") }
    return min + Random.nextDouble() * (max - min)
}

private fun getRandomLongitude(
    min: Float = DEFAULT_MIN_LONGITUDE, max: Float = DEFAULT_MAX_LONGITUDE
): Double {
    require(min < max) { Timber.i("Invalid range of LONGITUDE $min, $max]") }
    return min + Random.nextDouble() * (max - min)
}

