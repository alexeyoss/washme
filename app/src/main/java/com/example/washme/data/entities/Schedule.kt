package com.example.washme.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "schedule"
)
@Parcelize
@Immutable
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val schedule: String // // TODO challenge value TYPE
) : Parcelable