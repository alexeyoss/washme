package com.example.washme

import com.example.washme.utils.LocationLiveData

interface ApplicationViewModel {

    val locationLiveData: LocationLiveData
    fun startLocationUpdates()
    fun saveLocationIntoDB()
}