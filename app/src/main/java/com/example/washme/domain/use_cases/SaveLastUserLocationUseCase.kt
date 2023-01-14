package com.example.washme.domain.use_cases

import com.example.washme.data.entities.UserLocation
import com.example.washme.data.mappers.UserLocationMapper
import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.repository.LocationRepository
import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SaveLastUserLocationUseCase
@Inject constructor(
    private val locationRepository: LocationRepository,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userLocationMapper: UserLocationMapper
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Saving user location was failed - $throwable")
    }
    private val scope = CoroutineScope(ioDispatcher + SupervisorJob() + exceptionHandler)

    suspend operator fun invoke(userLocation: UserLocation): Flow<CommonStates> {
        return flow {
            emit(CommonStates.Loading)
            scope.launch {
                locationRepository.saveLastUserLocation(userLocation)
            }
        }
    }
}