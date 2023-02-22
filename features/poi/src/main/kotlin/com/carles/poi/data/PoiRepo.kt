package com.carles.poi.data

import com.carles.poi.Poi
import com.carles.poi.PoiDetail
import io.reactivex.Single

interface PoiRepo {

    fun getPoiList(refresh: Boolean = false): Single<List<Poi>>

    fun getPoiDetail(itemId: String, refresh: Boolean = false): Single<PoiDetail>
}
