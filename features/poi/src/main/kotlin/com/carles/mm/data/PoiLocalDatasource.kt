package com.carles.mm.data

import com.carles.core.data.Cache
import com.carles.core.data.CacheItems
import com.carles.core.data.CacheKey
import com.carles.core.data.ItemNotCachedException
import com.carles.mm.Poi
import com.carles.mm.PoiDetail
import io.reactivex.Single

class PoiLocalDatasource(private val dao: PoiDao, private val cache: Cache) {

    fun getPoiList(): Single<List<Poi>> =
        if (cache.isCached(CacheKey(CacheItems.POI_LIST))) {
            dao.loadPois()
        } else {
            Single.error(ItemNotCachedException)
        }

    fun persist(pois: List<Poi>) = with(pois) {
        dao.deletePois()
        dao.insertPois(pois)
        cache.set(CacheKey(CacheItems.POI_LIST))
        this
    }

    fun getPoiDetail(itemId: String): Single<PoiDetail> =
        if (cache.isCached(CacheKey(CacheItems.POI_DETAIL, itemId))) {
            dao.loadPoiById(itemId)
        } else {
            Single.error<PoiDetail>(ItemNotCachedException)
        }

    fun persist(poi: PoiDetail) = with(poi) {
        dao.insertPoi(poi)
        cache.set(CacheKey(CacheItems.POI_DETAIL, poi.id))
        this
    }
}