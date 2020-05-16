package com.carles.core

interface Navigator {
    fun up()
    fun toPoiDetailFromPoiList(id: String)
    fun toSettings()
    fun toErrorFromPoiList(errorMessage: String?)
    fun toErrorFromPoiDetail(errorMessage: String?)
}
