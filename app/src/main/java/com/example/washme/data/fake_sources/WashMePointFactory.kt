package com.example.washme.data.fake_sources

import com.example.washme.data.entities.WashMePoint
import com.example.washme.utils.ConstHolder.DEFAULT_MAX_LATITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MAX_LONGITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MIN_LATITUDE
import com.example.washme.utils.ConstHolder.DEFAULT_MIN_LONGITUDE
import timber.log.Timber
import kotlin.math.roundToInt
import kotlin.random.Random

/** [MapObjectsFactory] randomly provided the fake car wash points on the map */

class MapObjectsFactory {
    fun generateRandomPoints(amount: Int): List<WashMePoint> {
        return mutableListOf<WashMePoint>().apply {
            val idIterator = getRandomID(amount).iterator()
            repeat(amount) {
                add(
                    WashMePoint(
                        id = idIterator.nextInt().toLong(),
                        latitude = getRandomLatitude(),
                        longitude = getRandomLongitude(),
                        name = "Washing machines ${getRandomLatitude().roundToInt()}",
                        address = "Baker street ${getRandomLatitude().roundToInt()}",
                    )
                )
            }
        }
    }
}

private fun getRandomID(maxAmount: Int): IntRange = (1..maxAmount + 1)

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

