package com.example.washme.domain.use_cases

import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.repository.PointRepository
import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GenerateAndSavePointUseCase
@Inject constructor(
    private val pointRepository: PointRepository,
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(ioDispatcher)

    suspend operator fun invoke(amount: Int): Flow<CommonStates> {
        return flow {
            emit(CommonStates.Loading)
            val job = scope.async {
                coroutineScope {
                    val points = pointRepository.generateRandomPoints(amount)
                    pointRepository.savePointIntoDb(points)
                }
                pointRepository.getAllMapPointsFromDb()
            }
            try {
                val allCachedPoints = job.await()
                emit(CommonStates.Success(allCachedPoints))
            } catch (e: Exception) {
                emit(CommonStates.Error(e))
                Timber.e(e)
            }
        }
    }
}