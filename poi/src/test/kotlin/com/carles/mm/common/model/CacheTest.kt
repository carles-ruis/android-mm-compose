package com.carles.mm.common.model

import com.carles.core.data.Cache
import com.carles.core.data.CacheItems
import io.mockk.*
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class CacheTest {

    val cache = Cache()
    val calendar : Calendar = mockk()

    @Test
    fun isCached_itemNotCached() {
        Assertions.assertThat(cache.isCached(CacheItems.POI_LIST)).isFalse()
    }

    @Test
    fun isCached_itemExpired() {
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { calendar.timeInMillis } returns 0L
        cache.set(CacheItems.POI_LIST)

        clearStaticMockk(Calendar::class)
        Assertions.assertThat(cache.isCached(CacheItems.POI_LIST)).isFalse()
    }

    @Test
    fun isCached_itemExpired_Spy() {
        val spy : Cache = spyk()
        every { spy.now() } returns 0L
        spy.set(CacheItems.POI_LIST)

        clearAllMocks()
        Assertions.assertThat(spy.isCached(CacheItems.POI_LIST)).isFalse()
    }

    @Test
    fun isCached_success() {
        cache.set(CacheItems.POI_LIST)
        Assertions.assertThat(cache.isCached(CacheItems.POI_LIST)).isTrue()
    }
}