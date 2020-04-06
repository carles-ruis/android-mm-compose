package com.carles.poi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi_detail")
data class PoiDetail(
    @PrimaryKey
    var id: String,
    var title: String? = null,
    var address: String? = null,
    var transport: String? = null,
    var description: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var geocoordinates: String? = null
)