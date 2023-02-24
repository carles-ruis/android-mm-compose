package com.carles.hyrule.data.remote

import com.google.gson.annotations.SerializedName

data class MonstersResponseDto(
    @SerializedName("data")
    val data: List<MonsterDto>
)

data class MonsterDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

