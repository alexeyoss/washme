package com.example.washme.di

import com.example.washme.domain.repository.PointRepository
import com.example.washme.domain.use_cases.GenerateAndSavePointUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideGenerateAndSavePointUseCase(
        pointRepository: PointRepository,
        @CoroutinesModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GenerateAndSavePointUseCase {
        return GenerateAndSavePointUseCase(pointRepository, ioDispatcher)
    }
}