package com.carles.settings.data

import com.carles.common.data.Cache
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SettingsLocalDatasourceTest {

    private val cache: Cache = mockk(relaxed = true)
    private val datasource = SettingsLocalDatasource(cache)

    @Test
    fun updateCacheExpiration_success() {
        datasource.updateCacheExpiration().test()
        verify { cache.updateCacheExpiration() }
    }
}