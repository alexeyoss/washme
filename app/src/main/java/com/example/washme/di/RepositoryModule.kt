package com.example.washme.di

import com.example.washme.data.repository.LocationRepositoryImpl
import com.example.washme.data.repository.PointRepositoryImpl
import com.example.washme.domain.repository.LocationRepository
import com.example.washme.domain.repository.PointRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(impl: PointRepositoryImpl): PointRepository

    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}