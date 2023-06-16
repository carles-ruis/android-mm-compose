package com.carles.settings.ui

import com.carles.commontest.MainDispatcherRule
import com.carles.settings.R
import com.carles.settings.SettingsUi
import com.carles.settings.UserSettings
import com.carles.settings.domain.ObserveUserSettings
import com.carles.settings.domain.SetCacheExpirationTime
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val setCacheExpirationTime: SetCacheExpirationTime = mockk()
    private val observeUserSettings: ObserveUserSettings = mockk()
    private val settingsMapper: SettingsMapper = mockk()
    private val settingsUi: SettingsUi = mockk()
    private lateinit var viewModel: SettingsViewModel

    @Test
    fun `given initialization, when user settings are received, then update ui state with the settings`() = runTest {
        val userSettings = UserSettings(10)
        every { observeUserSettings.execute() } returns flow {
            emit(userSettings)
        }
        every { settingsMapper.toUi(any()) } returns settingsUi

        viewModel = SettingsViewModel(setCacheExpirationTime, observeUserSettings, settingsMapper)

        verify { observeUserSettings.execute() }
        verify { settingsMapper.toUi(userSettings) }
        assertTrue(viewModel.uiState.value == SettingsUiState(settings = settingsUi))
    }

    @Test
    fun `given initialization, when there is an error obtaining user settings, then update ui state with the error`() = runTest {
        val message = "error observing settings"
        every { observeUserSettings.execute() } returns flow {
            throw Throwable(message)
        }
        viewModel = SettingsViewModel(setCacheExpirationTime, observeUserSettings, settingsMapper)

        verify { observeUserSettings.execute() }
        assertTrue(viewModel.uiState.value == SettingsUiState(error = message))
    }

    @Test
    fun `given onSettingSelected, when new cache value is selected, then set new cache expiration time`() {
        every { observeUserSettings.execute() } returns flow {
            emit(UserSettings(10))
        }
        every { settingsMapper.toUi(any()) } returns settingsUi
        every { settingsMapper.toCacheExpirationTimeValue(any()) } returns 1
        coEvery { setCacheExpirationTime.execute(any()) } just Runs

        viewModel = SettingsViewModel(setCacheExpirationTime, observeUserSettings, settingsMapper)
        viewModel.onSettingSelected(R.string.preferences_cache_key, R.string.preferences_cache_one_minute)

        verify { settingsMapper.toCacheExpirationTimeValue(R.string.preferences_cache_one_minute) }
        coVerify { setCacheExpirationTime.execute(1) }
    }
}