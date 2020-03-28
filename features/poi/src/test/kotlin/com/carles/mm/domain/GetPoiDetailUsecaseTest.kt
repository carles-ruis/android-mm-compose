package com.carles.mm.domain

import com.carles.core.domain.AppSchedulers
import com.carles.mm.data.PoiRepository
import com.carles.mm.poiDetail
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class GetPoiDetailUsecaseTest {

    val scheduler = TestScheduler()
    val schedulers = AppSchedulers(scheduler, scheduler, scheduler)
    val repository: PoiRepository = mockk(relaxed = true)
    val usecase = GetPoiDetaiUsecase(repository, schedulers)

    @Test
    fun invoke_fetchFromRepo() {
        every { repository.getPoiDetail(any(),any()) } returns Single.just(poiDetail)

        val result = usecase.invoke("1").test()
        scheduler.triggerActions()

        verify { repository.getPoiDetail("1", false) }
        result.assertValue(poiDetail)
    }
}