package com.example.washme.data


sealed interface DataResponseState<out T> {
    data class Success<T>(val data: T) : DataResponseState<T>
    data class Error<T>(val data: T) : ErrorState
}

sealed interface ErrorState : DataResponseState<Nothing> {
    data class GenericError(val throwable: Throwable) : ErrorState
    object UnknownError : ErrorState
}