package com.example.washme.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber


fun <Response> buildRequestFlow(
    block: suspend () -> Response
): Flow<CommonStates> = flow {
    emit(CommonStates.Loading)
    try {
        val result = block()
        emit(
            CommonStates.Success(result)
        )
    } catch (e: Exception) {
        Timber.e(e)
        emit(CommonStates.Error(e))

    }
}

/** FlowCollector [collectOnLifecycle] overloading extensions */

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State,
    collector: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect {
                collector(it)
            }
        }
    }
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: AppCompatActivity,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.STARTED, collector)
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: Fragment,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.RESUMED, collector)
}

