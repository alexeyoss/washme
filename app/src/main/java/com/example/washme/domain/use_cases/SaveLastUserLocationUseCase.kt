package com.example.washme.domain.use_cases

import com.example.washme.data.entities.UserLocation
import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.repository.LocationRepository
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class SaveLastUserLocationUseCase
@Inject constructor(
    private val locationRepository: LocationRepository,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Saving user location process was failed - $throwable")
    }
    private val scope = CoroutineScope(ioDispatcher + SupervisorJob() + exceptionHandler)

    suspend operator fun invoke(userLocation: UserLocation) {
        scope.launch { locationRepository.saveLastUserLocation(userLocation) }
    }
}