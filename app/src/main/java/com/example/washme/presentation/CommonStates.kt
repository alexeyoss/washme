package com.example.washme.presentation

sealed interface CommonStates {
    data class Success<T>(internal val data: T) : CommonStates
    data class Error<T>(internal val error: T) : CommonStates
    object Loading : CommonStates
    object Empty : CommonStates
}