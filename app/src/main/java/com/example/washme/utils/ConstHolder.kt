package com.example.washme.utils

import androidx.room.RoomDatabase
import com.example.washme.data.entities.UserLocation

object ConstHolder {

    /** Default number of generated wash map points */
    const val DEFAULT_AMOUNT_OF_POINTS = 10

    /** [RoomDatabase]  version */
    const val WASHME_DB_VERSION = 1

    /** Declare refresh rate of user location updates [LocationLiveData]*/
    const val FIFTEEN_MINUTES = 900000L


    /** Default mapview if the location permissions will be restrict */
    private const val MOSCOW_LATITUDE = 55.751999
    private const val MOSCOW_LONGITUDE = 37.617734

    val DEFAULT_POINT_COORDINATION =
        UserLocation(latitude = MOSCOW_LATITUDE, longitude = MOSCOW_LONGITUDE)
}

