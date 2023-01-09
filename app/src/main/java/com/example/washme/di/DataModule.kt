package com.example.washme.di

import com.example.washme.data.MapObjectsFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMapObjectsFactory(): MapObjectsFactory {
        return MapObjectsFactory()
    }
}