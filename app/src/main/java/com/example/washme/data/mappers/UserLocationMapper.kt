package com.example.washme.data.mappers

import android.location.Location
import com.example.washme.data.entities.UserLocation

class UserLocationMapper : EntityMapper<UserLocation, Location> {
    override fun mapToModel(fromModel: Location): UserLocation {
        return UserLocation(
            latitude = fromModel.latitude,
            longitude = fromModel.longitude
        )
    }
}