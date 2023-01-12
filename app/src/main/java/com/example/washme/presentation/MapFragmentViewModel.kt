package com.example.washme.presentation

import kotlinx.coroutines.flow.StateFlow

interface MapFragmentViewModel {

    val pointsStateFlow: StateFlow<CommonStates>
    fun getStartRandomPoints(amount: Int)

}
