package com.example.washme.di

import com.example.washme.domain.GenerateAndSavePointUseCase
import com.example.washme.domain.PointRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideGenerateAndSavePointUseCase(pointRepository: PointRepository): GenerateAndSavePointUseCase {
        return GenerateAndSavePointUseCase(pointRepository)
    }
}