package com.example.washme.di

import android.content.Context
import androidx.room.Room
import com.example.washme.data.PointDao
import com.example.washme.data.PointStore
import com.example.washme.data.WashMeDB
import com.example.washme.data.fake_sources.MapObjectsFactory
import com.example.washme.utils.LocationLiveData
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
    fun provideWashMeDb(
        @ApplicationContext app: Context,
    ): WashMeDB {
        return Room.databaseBuilder(
            app,
            WashMeDB::class.java,
            "wash_me.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePointDao(washMeDb: WashMeDB): PointDao {
        return washMeDb.pointDao()
    }

    @Singleton
    @Provides
    fun providePointStore(
        pointDao: PointDao,
        @CoroutinesModule.IoDispatcher
        ioDispatcher: CoroutineDispatcher
    ): PointStore = PointStore(pointDao, ioDispatcher)


    @Singleton
    @Provides
    fun provideLocationLiveData(
        @ApplicationContext applicationContext: Context
    ): LocationLiveData = LocationLiveData(applicationContext)
}