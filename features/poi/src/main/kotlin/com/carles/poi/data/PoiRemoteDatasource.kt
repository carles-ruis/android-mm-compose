package com.carles.poi.data

class PoiRemoteDatasource(private val api: PoiApi) {

    fun getPoiList() = api.getPoiList().map { it.toModel() }

    fun getPoiDetail(itemId: String) = api.getPoiDetail(itemId).map { it.toModel() }
}