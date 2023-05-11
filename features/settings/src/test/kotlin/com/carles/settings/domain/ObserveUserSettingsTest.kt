package com.carles.settings.domain

import com.carles.common.domain.AppDispatchers
import com.carles.settings.UserSettings
import com.carles.settings.data.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveUserSettingsTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val dispatchers = AppDispatchers(dispatcher, dispatcher, dispatcher)
    private val repository: SettingsRepository = mockk()
    private lateinit var usecase: ObserveUserSettings

    @Before
    fun setup() {
        usecase = ObserveUserSettings(repository, dispatchers)
    }

    @Test
    fun `given usecase, when called, then observe user settings`() = runTest {
        val settings = UserSettings(10)

        every { repository.observeUserSettings() } returns flow { emit(settings) }
        val result = usecase.execute().first()
        verify { repository.observeUserSettings() }
        assertEquals(settings, result)
    }
}