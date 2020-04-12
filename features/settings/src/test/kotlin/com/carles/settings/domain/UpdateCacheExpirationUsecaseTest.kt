package com.carles.settings.domain

import com.carles.core.domain.AppSchedulers
import com.carles.settings.data.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class UpdateCacheExpirationUsecaseTest {

    val scheduler = TestScheduler()
    val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    val repository : SettingsRepository = mockk()
    val usecase = UpdateCacheExpirationUsecase(repository, schedulers)

    @Test
    fun invoke_updateSettings() {
        every { repository.updateCacheExpiration() } returns Completable.complete()
        val result = usecase.invoke().test()
        scheduler.triggerActions()
        result.assertComplete()
    }

}