package com.example.washme.di

import android.content.Context
import androidx.room.Room
import com.example.washme.data.*
import com.example.washme.data.fake_sources.MapObjectsFactory
import com.example.washme.data.mappers.UserLocationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMapObjectsFactory(): MapObjectsFactory {
        return MapObjectsFactory()
    }

    @Singleton
    @Provides
    fun provideUserLocationMapper(): UserLocationMapper {
        return UserLocationMapper()
    }


    @Singleton
    @Provides
    fun provideWashMeDb(
        @ApplicationContext app: Context,
    ): WashMeDB {
        return Room.databaseBuilder(
            app, WashMeDB::class.java, "wash_me.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePointDao(washMeDb: WashMeDB): PointDao {
        return washMeDb.pointDao()
    }

    @Singleton
    @Provides
    fun provideLocationDao(washMeDb: WashMeDB): LocationDao {
        return washMeDb.locationDao()
    }

    @Singleton
    @Provides
    fun providePointStore(
        pointDao: PointDao, @CoroutinesModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PointStore = PointStore(pointDao, ioDispatcher)


    @Singleton
    @Provides
    fun provideLocationStore(
        locationDao: LocationDao, @CoroutinesModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocationStore = LocationStore(locationDao, ioDispatcher)
}