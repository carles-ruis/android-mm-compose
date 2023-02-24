package com.carles.settings.domain

import com.carles.common.domain.AppSchedulers
import com.carles.settings.data.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class ResetCacheExpirationUsecaseTest {

    private val scheduler = TestScheduler()
    private val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    private val repository: SettingsRepository = mockk()
    private lateinit var usecase: ResetCacheExpirationUsecase

    @Before
    fun setup() {
        usecase = ResetCacheExpirationUsecase(repository, schedulers)
    }

    @Test
    fun `given usecase, when called, then reset cache from repository`() {
        every { repository.resetCacheExpiration() } returns Completable.complete()
        val observer = usecase.execute().test()
        scheduler.triggerActions()

        verify { repository.resetCacheExpiration() }
        observer.assertComplete()
    }
}
