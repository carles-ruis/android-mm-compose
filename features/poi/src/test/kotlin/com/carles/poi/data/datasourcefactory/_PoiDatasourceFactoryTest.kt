package com.carles.poi.data.datasourcefactory

import com.carles.core.data.Cache
import com.carles.core.data.CacheItems
import com.carles.core.data.CacheKey
import com.carles.poi.poi.data.datasourcefactory._PoiLocalDatasource
import com.carles.poi.poi.data.datasourcefactory._PoiRemoteDatasource
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.Test

class _PoiDatasourceFactoryTest {

    val localDatasource: _PoiLocalDatasource = mockk()
    val remoteDatasource: _PoiRemoteDatasource = mockk()
    val cache: Cache = mockk(relaxed = true)
    val factory = _PoiDatasourceFactory(localDatasource, remoteDatasource, cache)

    @Test
    fun retrieveLocalDatasource_returnsLocal() {
        Assertions.assertThat(factory.retrieveLocalDatasource()).isEqualTo(localDatasource)
    }

    @Test
    fun retrieveRemoteDatasource_returnsRemote() {
        Assertions.assertThat(factory.retrieveRemoteDatasource()).isEqualTo(remoteDatasource)
    }

    @Test
    fun retrieveDatasource_refresh() {
        Assertions.assertThat(factory.retrieveDatasource(true, CacheKey(CacheItems.POI_LIST)))
            .isEqualTo(remoteDatasource)
    }

    @Test
    fun retrieveDatasource_notCached() {
        every { cache.isCached(any()) } returns false
        Assertions.assertThat(factory.retrieveDatasource(false, CacheKey(CacheItems.POI_LIST)))
            .isEqualTo(remoteDatasource)
    }

    @Test
    fun retrieveDatasource_cached() {
        every { cache.isCached(any()) } returns true
        Assertions.assertThat(factory.retrieveDatasource(false, CacheKey(CacheItems.POI_LIST)))
            .isEqualTo(localDatasource)
    }
}