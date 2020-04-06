package com.carles.poi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.carles.core.ui.viewmodel.ERROR
import com.carles.core.ui.viewmodel.Resource
import com.carles.core.ui.viewmodel.SUCCESS
import com.carles.poi.Poi
import com.carles.poi.domain.FetchPoiListUsecase
import com.carles.poi.poiList
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Rule
import org.junit.Test

class PoiListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val observer: Observer<Resource<List<Poi>>> = mockk()
    val fetchPoiListUsecase: FetchPoiListUsecase = mockk(relaxed = true)
    lateinit var viewModel: PoiListViewModel

    @Test
    fun init_getPoiList_success() {
        every { fetchPoiListUsecase.invoke() } returns Single.just(poiList)

        viewModel = PoiListViewModel(fetchPoiListUsecase)

        Assertions.assertThat(viewModel.observablePoiList.value!!.state).isEqualTo(SUCCESS)
        Assertions.assertThat(viewModel.observablePoiList.value!!.data).isEqualTo(poiList)
    }

    @Test
    fun init_getPoiList_error() {
        val error = Throwable("some error")
        every { fetchPoiListUsecase.invoke() } returns Single.error(Throwable("some error"))

        viewModel = PoiListViewModel(fetchPoiListUsecase)

        Assertions.assertThat(viewModel.observablePoiList.value!!.state).isEqualTo(ERROR)
        Assertions.assertThat(viewModel.observablePoiList.value!!.message).isEqualTo("some error")
    }

    @Test
    fun retry_getPoiList() {
        viewModel = PoiListViewModel(fetchPoiListUsecase)
        clearMocks(fetchPoiListUsecase)

        viewModel.retry()
        verify(exactly = 1) { fetchPoiListUsecase.invoke() }
    }
}