package com.example.washme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.washme.utils.LocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel
@Inject
constructor(application: Application) : AndroidViewModel(application) {

    private val _locationLiveData = LocationLiveData(application)
    val locationLiveData = _locationLiveData

    fun startLocationUpdates(){
        _locationLiveData.startLocationUpdate()
    }
}