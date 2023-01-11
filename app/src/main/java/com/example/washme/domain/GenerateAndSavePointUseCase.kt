package com.example.washme.domain

import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateAndSavePointUseCase
@Inject
constructor(
    private val pointRepository: PointRepository,
) {

    suspend operator fun invoke(amount: Int): Flow<CommonStates> {
        return flow {
            emit(CommonStates.Loading)
            try {
                val points = pointRepository.generateRandomPoints(amount)
                pointRepository.savePointIntoDb(points)
                emit(CommonStates.Success(points))
            } catch (e: Exception) {
                emit(CommonStates.Error(e))
            }
        }
    }
}