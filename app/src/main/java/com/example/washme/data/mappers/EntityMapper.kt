package com.example.washme.data.mappers

import android.location.Location
import com.yandex.mapkit.geometry.Point

interface EntityMapper<DomainModel> {
//    fun mapToModel(fromModel: Point): DomainModel
    fun mapToModel(fromModel: Location): DomainModel
}