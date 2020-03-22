package com.carles.mm.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PoiApi {

    @GET("points")
    fun getPoiList(): Single<PoiListResponseDto>

    @GET("points/{id}")
    fun getPoiDetail(@Path("id") id: String): Single<PoiDetailResponseDto>
}