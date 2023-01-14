package com.example.washme.di

import android.content.Context
import com.example.washme.App
import com.example.washme.data.mappers.UserLocationMapper
import com.example.washme.utils.LocationLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) = app as App


    @Singleton
    @Provides
    fun provideLocationLiveData(
        @ApplicationContext applicationContext: Context,
        userLocationMapper: UserLocationMapper
    ): LocationLiveData =
        LocationLiveData(applicationContext, userLocationMapper)
}