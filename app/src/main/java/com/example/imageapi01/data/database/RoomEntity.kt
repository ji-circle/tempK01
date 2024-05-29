package com.example.imageapi01.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity(
    @PrimaryKey val thumbnailUrl: String,
    @ColumnInfo(name = "display_sitename") val siteName: String,
    @ColumnInfo(name = "datetime") val dateTime: String,

    )

