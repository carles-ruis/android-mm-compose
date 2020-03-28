package com.carles.mm.data

import com.carles.mm.data.toModel
import com.carles.mm.emptyPoiListResponseDto
import com.carles.mm.Poi
import com.carles.mm.poiDetailResponseDto
import com.carles.mm.poiListResponseDto
import com.carles.mm.poiResponseDto
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class DtoTest {

    @Test
    fun poiListResponseDto_toModel() {
        Assertions.assertThat(emptyPoiListResponseDto.toModel()).isEqualTo(Collections.emptyList<Poi>())
        with(poiListResponseDto.toModel()) {
            Assertions.assertThat(size == poiListResponseDto.list!!.size && get(0).id == poiListResponseDto.list!!.get(0).id)
        }
    }

    @Test
    fun poiResponseDto_toModel() {
        with(poiResponseDto.toModel()) {
            Assertions.assertThat(id == poiResponseDto.id && title == poiResponseDto.title && geocoordinates == poiResponseDto.geocoordinates)
        }
    }

    @Test
    fun poiDetailResponseDto_toModel() {
        with(poiDetailResponseDto.toModel()) {
            Assertions.assertThat(
                id == poiDetailResponseDto.id && transport == poiDetailResponseDto.transport && email ==
                        poiDetailResponseDto.email && phone == poiDetailResponseDto.phone
            )
        }
        with(poiDetailResponseDto.copy(transport = "", email = "null", phone = "undefined").toModel()) {
            Assertions.assertThat(transport == null && email == null && phone == null)
        }
    }
}