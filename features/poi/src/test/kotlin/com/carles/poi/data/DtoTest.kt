package com.carles.poi.data

import com.carles.poi.*
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