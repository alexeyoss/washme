package com.example.washme.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washme.data.MainRepositoryImpl
import com.example.washme.di.CoroutinesModule
import com.example.washme.utils.ConstHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel
@Inject
constructor(
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val mainRepository: MainRepositoryImpl
) : ViewModel() {

    private val _points = MutableStateFlow<CommonStates>(CommonStates.Empty)
    val points = _points.asStateFlow()

    init {
        getRandomPoints(ConstHolder.DEFAULT_AMOUNT_OF_POINTS)
    }

    private fun getRandomPoints(amount: Int) {
        viewModelScope.launch(ioDispatcher) {
            mainRepository.generateRandomPoints(amount)
                .onEach { commonState ->
                    _points.emit(commonState)
                }.launchIn(viewModelScope)
        }
    }
}
