package com.carles.settings.domain

import com.carles.common.domain.AppDispatchers
import com.carles.settings.data.SettingsRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SetCacheExpirationTimeTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val dispatchers = AppDispatchers(dispatcher, dispatcher, dispatcher)
    private val repository: SettingsRepository = mockk()
    private lateinit var usecase: SetCacheExpirationTime

    @Before
    fun setup() {
        usecase = SetCacheExpirationTime(repository, dispatchers)
    }

    @Test
    fun `given usecase, when called, then reset cache from repository`() = runTest {
        coEvery { repository.setCacheExpirationTime(any()) } just Runs
        usecase.execute(10)
        coVerify { repository.setCacheExpirationTime(10) }
    }
}
