package com.example.washme.data.fake_sources

import com.example.washme.data.entities.UserLocation
import com.example.washme.data.entities.WashMePoint
import timber.log.Timber
import kotlin.random.Random

/** [MapObjectsFactory] randomly provided the fake car wash points on the map */

class MapObjectsFactory {
    fun generateRandomPoints(amount: Int, location: UserLocation): List<WashMePoint> {
        return mutableListOf<WashMePoint>().apply {
            val idIterator = getRandomID(amount).iterator()

            repeat(amount) {
                add(
                    WashMePoint(
                        id = idIterator.nextInt().toLong(),
                        latitude = getRandomLatitude(location),
                        longitude = getRandomLongitude(location),
                        name = "Washing machines ${Random.nextInt(0, 50)}",
                        address = "Baker street ${Random.nextInt(0, 50)}}",
                    )
                )
            }
        }
    }
}

const val DIFF = 0.05
private fun getRandomID(maxAmount: Int): IntRange = (1..maxAmount + 1)

private fun getRandomLatitude(
    location: UserLocation
): Double {
    val min = location.latitude - DIFF
    val max = location.latitude + DIFF
    require(min < max) { Timber.i("Invalid range of LATITUDE [$min, $max]") }
    return Random.nextDouble(min, max)
}

private fun getRandomLongitude(
    location: UserLocation
): Double {
    val min = location.longitude - DIFF
    val max = location.longitude + DIFF
    require(min < max) { Timber.i("Invalid range of LONGITUDE $min, $max]") }
    return Random.nextDouble(min, max)
}

