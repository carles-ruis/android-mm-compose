package com.carles.settings.data

import com.carles.core.data.Cache
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SettingsLocalDatasourceTest {

    val cache : Cache = mockk(relaxed = true)
    val datasource = SettingsLocalDatasource(cache)

    @Test
    fun updateCacheExpiration_success() {
        datasource.updateCacheExpiration().test()
        verify { cache.updateCacheExpiration() }
    }

}