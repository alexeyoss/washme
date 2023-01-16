package com.example.washme.data.entities

import android.os.Parcelable
import androidx.room.*
import com.yandex.mapkit.geometry.Point
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "points", indices = [Index("phone", unique = true)], foreignKeys = [ForeignKey(
        entity = Schedule::class,
        parentColumns = ["id"],
        childColumns = ["schedule_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
@Immutable
data class WashMePoint(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "schedule_id", index = true) val scheduleId: Long? = null,
    @ColumnInfo(name = "working_hours") val workingHours: String? = null, // TODO challenge value TYPE
    @ColumnInfo(name = "category") val category: Int? = null, // TODO challenge value TYPE
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "logo_url") val logoUrl: String? = null,
    @ColumnInfo(name = "photo_url") val photoUrl: String? = null,
    @ColumnInfo(name = "country_code") val countryCode: Int? = null,
    @ColumnInfo(name = "phone") val phone: Int? = null,
    @ColumnInfo(name = "website") val website: String? = null,
//    @ColumnInfo(name = "updated_at") val updatedAt: String? = null, // TODO when implement job scheduling
) : Parcelable {

    companion object {
        fun WashMePoint.toYandexPoint(): Point = Point(
            this.latitude, this.longitude
        )
    }
}