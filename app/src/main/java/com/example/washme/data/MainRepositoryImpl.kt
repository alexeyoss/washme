package com.example.washme.data

import com.example.washme.domain.MainRepository
import com.example.washme.presentation.CommonStates
import com.example.washme.utils.buildRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl
@Inject
constructor(
    private val mapObjectsFactory: MapObjectsFactory
) : MainRepository {

    override suspend fun generateRandomPoints(amount: Int): Flow<CommonStates> {
        return buildRequestFlow {
            mapObjectsFactory.generateRandomPoints(amount)
        }
    }
}