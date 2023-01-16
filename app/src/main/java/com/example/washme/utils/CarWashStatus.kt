package com.example.washme.utils

import androidx.annotation.DrawableRes
import com.example.washme.R

enum class CarWashStatus(
    @DrawableRes drawable: Int
) {
    OPEN(R.drawable.ic_open_status),
    NO_DATA(R.drawable.ic_nodata_status),
    CLOSED(R.drawable.ic_closed_status)
}