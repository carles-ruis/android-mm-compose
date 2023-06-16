package com.carles.settings.data

import com.carles.common.data.AppDatastore
import com.carles.common.data.Cache
import com.carles.settings.UserSettings
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SettingsRepositoryTest {

    private val cache: Cache = mockk()
    private val datastore: AppDatastore = mockk()
    private lateinit var repository: SettingsRepository
    private lateinit var spy: SettingsRepository

    @Before
    fun setup() {
        repository = SettingsRepository(cache, datastore)
        spy = spyk(SettingsRepository(cache, datastore))
    }

    @Test
    fun `given setCacheExpirationTime, when called, then reset cache, store new value and emit`() = runTest {
        coEvery { cache.resetCacheExpiration() } just Runs
        coEvery { datastore.setCacheExpirationTime(any()) } just Runs

        repository.setCacheExpirationTime(10)

        coVerify { cache.resetCacheExpiration() }
        coVerify { datastore.setCacheExpirationTime(10) }
    }

    @Test
    fun `given getUserSettings, when called, then return UserSettings object with preferences value`() = runTest {
        coEvery { datastore.getCacheExpirationTime() } returns flow { emit(10) }
        val result = repository.observeUserSettings().first()
        coVerify { datastore.getCacheExpirationTime() }
        assertEquals(UserSettings(10), result)
    }
}