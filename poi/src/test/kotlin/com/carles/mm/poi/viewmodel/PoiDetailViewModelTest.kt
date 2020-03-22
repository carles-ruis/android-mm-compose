package com.carles.mm.poi.viewmodel

import com.carles.mm.domain.GetPoiDetaiUsecase
import com.carles.mm.ui.viewmodel.PoiDetailViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class PoiDetailViewModelTest {

    val getPoiDetailUsecase : GetPoiDetaiUsecase = mockk(relaxed = true)

    @Before
    fun setup() {
        PoiDetailViewModel("1", getPoiDetailUsecase)
    }

    @Test
    fun init_getPoiDetail() {
        verify { getPoiDetailUsecase.invoke("1") }
    }
}