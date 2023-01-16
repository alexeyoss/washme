package com.example.washme.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washme.data.entities.UserLocation
import com.example.washme.di.CoroutinesModule
import com.example.washme.domain.use_cases.GenerateAndSavePointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModelImpl
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val generateAndSavePointUseCase: GenerateAndSavePointUseCase
) : ViewModel(), MainActivityViewModel {
    // TODO save the map state while view rendering

    private val _pointsStateFlow = MutableStateFlow<CommonStates>(CommonStates.Empty)
    override val pointsStateFlow = _pointsStateFlow.asStateFlow()

    override fun getStartRandomPoints(amount: Int, location: UserLocation) {
        viewModelScope.launch(ioDispatcher) {
            generateAndSavePointUseCase.invoke(amount, location).onEach { commonState ->
                _pointsStateFlow.emit(commonState)
            }.launchIn(viewModelScope)
        }
    }
}
