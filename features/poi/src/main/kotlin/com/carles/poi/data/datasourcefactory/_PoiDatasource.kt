@file:Suppress("ClassNaming", "Filename")
package com.carles.poi.data.datasourcefactory

import com.carles.core.data.Cache
import com.carles.core.data.CacheItems
import com.carles.core.data.CacheKey
import com.carles.poi.Poi
import com.carles.poi.PoiDetail
import com.carles.poi.data.PoiApi
import com.carles.poi.data.PoiDao
import com.carles.poi.data.toModel
import io.reactivex.Single

interface _PoiDatasource {

    fun getPoiList(): Single<List<Poi>>

    fun getPoiDetail(itemId: String): Single<PoiDetail>
}

class _PoiLocalDatasource(private val cache: Cache, private val dao: PoiDao) : _PoiDatasource {

    override fun getPoiList() = dao.loadPois()

    fun persist(pois: List<Poi>): Single<List<Poi>> {
        dao.deletePois()
        dao.insertPois(pois)
        cache.set(CacheKey(CacheItems.POI_LIST))
        return Single.just(pois)
    }

    override fun getPoiDetail(itemId: String) = dao.loadPoiById(itemId)

    fun persist(poi: PoiDetail): Single<PoiDetail> {
        dao.insertPoi(poi)
        cache.set(CacheKey(CacheItems.POI_DETAIL, poi.id))
        return Single.just(poi)
    }
}

@SuppressWarnings("ClassNaming")
class _PoiRemoteDatasource(private val api: PoiApi) : _PoiDatasource {

    override fun getPoiList() = api.getPoiList().map { it.toModel() }

    override fun getPoiDetail(itemId: String) = api.getPoiDetail(itemId).map { it.toModel() }
}