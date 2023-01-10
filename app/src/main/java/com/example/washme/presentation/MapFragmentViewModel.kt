package com.example.washme.presentation

import com.example.washme.utils.ConstHolder
import kotlinx.coroutines.flow.StateFlow

interface MapFragmentViewModel {

    val pointsStateFlow: StateFlow<CommonStates>
    fun getStartRandomPoints(amount: Int)

}
