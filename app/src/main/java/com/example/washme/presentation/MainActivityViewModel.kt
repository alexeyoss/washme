package com.example.washme.presentation

import com.example.washme.data.entities.UserLocation
import kotlinx.coroutines.flow.StateFlow

interface MainActivityViewModel {

    val pointsStateFlow: StateFlow<CommonStates>
    fun getStartRandomPoints(amount: Int, location: UserLocation)

}
