package com.example.washme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.washme.data.entities.UserLocation
import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.use_cases.SaveLastUserLocationUseCase
import com.example.washme.utils.LocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ApplicationViewModelImpl
@Inject
constructor(
    application: Application,
    private val saveLastUserLocationUseCase: SaveLastUserLocationUseCase,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val _locationLiveData: LocationLiveData
) : AndroidViewModel(application), ApplicationViewModel {

    override val locationLiveData = _locationLiveData

    override fun startLocationUpdates() {
        locationLiveData.startLocationUpdate()
    }

    override fun saveLocationIntoDB() {
        viewModelScope.launch(ioDispatcher) {
            saveLastUserLocationUseCase.invoke(locationLiveData.value!!)
        }
    }


}