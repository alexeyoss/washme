package com.example.washme.di

import com.example.washme.data.MainRepositoryImpl
import com.example.washme.domain.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository
}