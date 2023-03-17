package com.carles.settings.domain

import com.carles.common.domain.AppSchedulers
import com.carles.settings.UserSettings
import com.carles.settings.data.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class ObserveUserSettingsTest {

    private val scheduler = TestScheduler()
    private val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    private val repository: SettingsRepository = mockk()
    private lateinit var usecase: ObserveUserSettings

    @Before
    fun setup() {
        usecase = ObserveUserSettings(repository, schedulers)
    }

    @Test
    fun `given usecase, when called, then observe user settings`() {
        val expected = UserSettings(10)

        every { repository.observeUserSettings() } returns Flowable.just(expected)
        val subscriber = usecase.execute().test()
        scheduler.triggerActions()

        subscriber.assertValue(expected)
        subscriber.assertComplete()
    }
}