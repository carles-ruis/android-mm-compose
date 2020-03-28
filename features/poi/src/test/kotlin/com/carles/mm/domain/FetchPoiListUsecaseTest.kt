package com.carles.mm.domain

import com.carles.core.domain.AppSchedulers
import com.carles.mm.data.PoiRepository
import com.carles.mm.poiList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class FetchPoiListUsecaseTest {

    val repository : PoiRepository = mockk(relaxed = true)
    val scheduler = TestScheduler()
    val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    val usecase = FetchPoiListUsecase(repository, schedulers)

    @Test
    fun invoke_fetchRepository() {
        every { repository.getPoiList(any()) } returns Single.just(poiList)

        val result = usecase.invoke().test()
        scheduler.triggerActions()

        verify { repository.getPoiList(true) }
        result.assertValue(poiList)
    }
}