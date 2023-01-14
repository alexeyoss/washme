package com.example.washme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.use_cases.SaveLastUserLocationUseCase
import com.example.washme.utils.LocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

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
        _locationLiveData.startLocationUpdate()

        // TODO troubles, debug!!!!
//        _locationLiveData.observe(getApplication()) { userLocation ->
//            viewModelScope.launch(ioDispatcher) {
//                saveLastUserLocationUseCase.invoke(userLocation)
//            }
//        }
    }


}