package com.carles.settings.data

import com.carles.common.data.Cache
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SettingsRepositoryTest {

    private val cache: Cache = mockk()
    private lateinit var repository: SettingsRepository

    @Before
    fun setup() {
        repository = SettingsRepository(cache)
    }

    @Test
    fun `given resetCacheExpiration, when called, then reset cache`() {
        every { cache.resetCacheExpiration() } just runs
        val observer = repository.resetCacheExpiration().test()

        verify { cache.resetCacheExpiration() }
        observer.assertComplete()
    }
}