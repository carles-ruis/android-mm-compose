package com.carles.settings.data

import com.carles.common.data.AppPreferences
import com.carles.common.data.Cache
import com.carles.settings.UserSettings
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsRepositoryTest {

    private val cache: Cache = mockk()
    private val preferences: AppPreferences = mockk()
    private lateinit var repository: SettingsRepository
    private lateinit var spy: SettingsRepository

    @Before
    fun setup() {
        repository = SettingsRepository(cache, preferences)
        spy = spyk(SettingsRepository(cache, preferences))
    }

    @Test
    fun `given setCacheExpirationTime, when called, then reset cache, store new value and emit`() {
        every { cache.resetCacheExpiration() } just Runs
        every { preferences getProperty AppPreferences::cacheExpirationTime.name } returns 0
        every { preferences setProperty AppPreferences::cacheExpirationTime.name value any<Int>() } just Runs

        val observer = spy.setCacheExpirationTime(10).test()

        verify { cache.resetCacheExpiration() }
        verify { preferences.cacheExpirationTime = 10 }
        verify { spy.emitUserSettings() }
        observer.assertComplete()
    }

    @Test
    fun `given getUserSettings, when called, then return UserSettings object with preferences value`() {
        every { preferences getProperty AppPreferences::cacheExpirationTime.name } returns 10
        assertEquals(UserSettings(10), repository.getUserSettings())
    }
}