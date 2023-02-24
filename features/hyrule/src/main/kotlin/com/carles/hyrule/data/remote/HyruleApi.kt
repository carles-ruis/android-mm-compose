package com.carles.hyrule.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HyruleApi {

    @GET("api/v2/category/monsters")
    fun getMonsters(): Single<MonstersResponseDto>

    @GET("api/v2/entry/{id}")
    fun getMonsterDetail(@Path("id") id: String): Single<MonsterDetailResponseDto>
}