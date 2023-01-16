package com.example.washme.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.washme.data.DataResponseState
import com.example.washme.data.ErrorState
import com.example.washme.presentation.CommonStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

suspend fun <T> safeCall(
    block: suspend () -> T
): DataResponseState<T> = runCatching {
    DataResponseState.Success(block())
}.getOrElse {
    checkThrowable(it)
}

private fun checkThrowable(throwable: Throwable): DataResponseState<Nothing> = when (throwable) {
    is UnknownHostException -> ErrorState.GenericError(throwable)
    else -> ErrorState.UnknownError
}

fun <Response> buildRequestFlow(
    block: suspend () -> DataResponseState<Response>
): Flow<CommonStates> {
    return flow {
        emit(CommonStates.Loading)
        val result = when (val request = block()) {
            is DataResponseState.Success -> CommonStates.Success(request.data)
            else -> CommonStates.Error(request)
        }
        emit(result)
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

fun <T> lazyUnsafe(block: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { block() }
}

