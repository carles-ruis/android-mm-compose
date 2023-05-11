package com.carles.hyrule.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface HyruleApi {

    @GET("api/v2/category/monsters")
    suspend fun getMonsters(): MonstersResponseDto

    @GET("api/v2/entry/{id}")
    suspend fun getMonsterDetail(@Path("id") id: String): MonsterDetailResponseDto
}