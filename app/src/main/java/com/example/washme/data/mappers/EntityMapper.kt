package com.example.washme.data.mappers

import android.location.Location
import com.yandex.mapkit.geometry.Point

interface EntityMapper<DomainModel, ForeignModel> {
    fun mapToModel(fromModel: ForeignModel): DomainModel
}