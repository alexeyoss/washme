package com.example.washme.presentation

import com.example.washme.data.entities.UserLocation
import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.flow.StateFlow

interface MapFragmentViewModel {

    val pointsStateFlow: StateFlow<CommonStates>
    fun getStartRandomPoints(amount: Int, location: UserLocation)

}
