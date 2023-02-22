package com.carles.poi.domain

import com.carles.core.domain.AppSchedulers
import com.carles.poi.data.PoiRepository
import com.carles.poi.poiList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class FetchPoiListUsecaseTest {

    private val repository: PoiRepository = mockk(relaxed = true)
    private val scheduler = TestScheduler()
    private val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    private val usecase = FetchPoiListUsecase(repository, schedulers)

    @Test
    fun invoke_fetchRepository() {
        every { repository.getPoiList(any()) } returns Single.just(poiList)

        val result = usecase.invoke().test()
        scheduler.triggerActions()

        verify { repository.getPoiList(true) }
        result.assertValue(poiList)
    }
}