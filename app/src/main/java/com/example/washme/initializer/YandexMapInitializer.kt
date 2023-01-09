package com.example.washme.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.washme.BuildConfig
import com.yandex.mapkit.MapKitFactory

class YandexMapInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}