package com.carles.settings.data

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SettingsRepositoryTest() {

    val datasource : SettingsLocalDatasource = mockk(relaxed = true)
    val repository = SettingsRepository(datasource)

    @Test
    fun updateCacheExpiration_updateLocalSettings() {
        repository.updateCacheExpiration()
        verify { datasource.updateCacheExpiration() }
    }

}