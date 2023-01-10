package com.example.washme.domain

import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun generateRandomPoints(amount: Int): Flow<CommonStates>
}