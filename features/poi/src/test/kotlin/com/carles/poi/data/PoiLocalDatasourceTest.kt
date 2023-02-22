package com.carles.poi.data

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.common.data.ItemNotCachedException
import com.carles.poi.poiDetail
import com.carles.poi.poiList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import io.reactivex.Single
import org.junit.Test

class PoiLocalDatasourceTest {

    val cache: Cache = mockk(relaxed = true)
    val dao: PoiDao = mockk(relaxed = true)
    val datasource = PoiLocalDatasource(dao, cache)

    @Test
    fun getPoiList_cacheHit() {
        every { cache.isCached(CacheKey(CacheItems.POI_LIST)) } returns true
        every { dao.loadPois() } returns Single.just(poiList)
        datasource.getPoiList().test().assertValue(poiList)
    }

    @Test
    fun getPoiList_cacheMiss() {
        every { cache.isCached(CacheKey(CacheItems.POI_LIST)) } returns false
        datasource.getPoiList().test().assertError(ItemNotCachedException)
    }

    @Test
    fun persist_poiList() {
        datasource.persist(poiList)
        verifyAll {
            dao.deletePois()
            dao.insertPois(poiList)
            cache.set(CacheKey(CacheItems.POI_LIST))
        }
    }

    @Test
    fun getPoiDetail_cacheHit() {
        every { cache.isCached(CacheKey(CacheItems.POI_DETAIL, "1")) } returns true
        every { dao.loadPoiById("1") } returns Single.just(poiDetail)
        datasource.getPoiDetail("1").test().assertValue(poiDetail)
    }

    @Test
    fun getPoiDetail_cacheMiss() {
        every { cache.isCached(CacheKey(CacheItems.POI_DETAIL, "1")) } returns false
        datasource.getPoiDetail("1").test().assertError(ItemNotCachedException)
    }

    @Test
    fun persist_poiDetail() {
        datasource.persist(poiDetail)
        verifyAll {
            dao.insertPoi(poiDetail)
            cache.set(CacheKey(CacheItems.POI_DETAIL, "1"))
        }
    }
}