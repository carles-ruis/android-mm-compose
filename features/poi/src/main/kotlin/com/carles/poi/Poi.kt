package com.carles.poi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi")
data class Poi(
    @PrimaryKey
    var id: String,
    var title: String,
    var geocoordinates: String
)
