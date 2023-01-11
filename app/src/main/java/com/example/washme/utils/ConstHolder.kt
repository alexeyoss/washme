package com.example.washme.utils

import androidx.room.RoomDatabase

object ConstHolder {
    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    /** Consts for fake points generator */
    const val DEFAULT_AMOUNT_OF_POINTS = 10
    const val DEFAULT_MAX_LATITUDE = 50f
    const val DEFAULT_MIN_LATITUDE = 0f
    const val DEFAULT_MAX_LONGITUDE = 50f
    const val DEFAULT_MIN_LONGITUDE = 0f

    /** [RoomDatabase]  version */
    const val WASHME_DB_VERSION = 1

}
