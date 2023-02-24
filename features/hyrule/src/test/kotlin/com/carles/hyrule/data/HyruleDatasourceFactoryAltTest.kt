package com.carles.hyrule.data

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.hyrule.data.local.HyruleLocalDatasourceAlt
import com.carles.hyrule.data.remote.HyruleRemoteDatasourceAlt
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HyruleDatasourceFactoryAltTest {

    private val localDatasource: HyruleLocalDatasourceAlt = mockk()
    private val remoteDatasource: HyruleRemoteDatasourceAlt = mockk()
    private val cache: Cache = mockk()
    private val key = CacheKey(CacheItems.MONSTERS)
    private lateinit var factory: HyruleDatasourceFactoryAlt

    @Before
    fun setup() {
        factory = HyruleDatasourceFactoryAlt(localDatasource, remoteDatasource, cache)
    }

    @Test
    fun `given provideDatasource, when refresh is true, then return remote datasource`() {
        every { cache.isCached(any()) } returns true
        assertEquals(remoteDatasource, factory.provideDatasource(key, true))
    }

    @Test
    fun `given provideDatasource, when refresh is false and item is cached, then return local datasource`() {
        every { cache.isCached(any()) } returns true
        assertEquals(localDatasource, factory.provideDatasource(key, false))
    }

    @Test
    fun `given provideDatasource, when item is not cached, then return remote datasource`() {
        every { cache.isCached(any()) } returns false
        assertEquals(remoteDatasource, factory.provideDatasource(key))
    }
}