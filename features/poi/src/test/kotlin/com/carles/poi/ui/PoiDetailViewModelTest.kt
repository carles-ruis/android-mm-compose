package com.carles.poi.ui

import androidx.arch.common.executor.testing.InstantTaskExecutorRule
import com.carles.common.ui.ERROR
import com.carles.common.ui.SUCCESS
import com.carles.poi.domain.GetPoiDetaiUsecase
import com.carles.poi.poiDetail
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.assertj.common.api.Assertions
import org.junit.Rule
import org.junit.Test

class PoiDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val getPoiDetailUsecase: GetPoiDetaiUsecase = mockk(relaxed = true)
    lateinit var viewModel: PoiDetailViewModel

    @Test
    fun init_getPoiDetail_success() {
        every { getPoiDetailUsecase.invoke(any()) } returns Single.just(poiDetail)

        viewModel = PoiDetailViewModel("1", getPoiDetailUsecase)

        Assertions.assertThat(viewModel.observablePoiDetail.value!!.state).isEqualTo(SUCCESS)
        Assertions.assertThat(viewModel.observablePoiDetail.value!!.data).isEqualTo(poiDetail)
    }

    @Test
    fun init_getPoiDetail_error() {
        val error = Throwable("some error")
        every { getPoiDetailUsecase.invoke(any()) } returns Single.error(error)

        viewModel = PoiDetailViewModel("1", getPoiDetailUsecase)

        Assertions.assertThat(viewModel.observablePoiDetail.value!!.state).isEqualTo(ERROR)
        Assertions.assertThat(viewModel.observablePoiDetail.value!!.message).isEqualTo("some error")
    }
}