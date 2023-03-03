package com.carles.settings.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carles.settings.domain.ResetCacheExpiration
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val resetCacheExpiration: ResetCacheExpiration = mockk()
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(resetCacheExpiration)
    }

    @Test
    fun `given onPreferenceCacheChanged, when called, then reset cache expiration`() {
        every { resetCacheExpiration.execute() } returns Completable.complete()
        viewModel.onPreferenceCacheChanged()
        verify { resetCacheExpiration.execute() }
    }
}