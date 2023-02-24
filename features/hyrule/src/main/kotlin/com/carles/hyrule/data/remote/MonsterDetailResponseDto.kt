package com.carles.hyrule.data.remote

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MonsterDetailResponseDto(
    @SerializedName("data")
    val data: MonstersDetailDto
)

data class MonstersDetailDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("common_locations")
    val commonLocations: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("category")
    val category: String
)