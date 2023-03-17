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

class SetCacheExpirationTimeTest {

    private val scheduler = TestScheduler()
    private val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    private val repository: SettingsRepository = mockk()
    private lateinit var usecase: SetCacheExpirationTime

    @Before
    fun setup() {
        usecase = SetCacheExpirationTime(repository, schedulers)
    }

    @Test
    fun `given usecase, when called, then reset cache from repository`() {
        every { repository.setCacheExpirationTime(any()) } returns Completable.complete()
        val observer = usecase.execute(10).test()
        scheduler.triggerActions()

        verify { repository.setCacheExpirationTime(10) }
        observer.assertComplete()
    }
}
