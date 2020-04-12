package com.carles.core.data

import io.mockk.clearAllMocks
import io.mockk.clearStaticMockk
import io.mockk.every
import io.mockk.impl.instantiation.JvmMockFactoryHelper.cache
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class CacheTest {

    val preferences : AppPreferences = mockk(relaxed = true)
    val cache = Cache(preferences)
    val calendar : Calendar = mockk()
    val cacheKey = CacheKey(CacheItems.POI_LIST)

    @Test
    fun isCached_itemNotCached() {
        Assertions.assertThat(cache.isCached(cacheKey)).isFalse()
    }

    @Test
    fun isCached_itemExpired() {
        every { preferences.cacheExpirationTime } returns 0
        cache.set(cacheKey)
        Assertions.assertThat(cache.isCached(cacheKey)).isFalse()
    }
 /*
    @Test
    fun isCached_itemExpired_MockStaticCalendar() {
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { calendar.timeInMillis } returns 0L
        cache.set(cacheKey)

        clearStaticMockk(Calendar::class)
        Assertions.assertThat(cache.isCached(cacheKey)).isFalse()
    }

    @Test
    fun isCached_itemExpired_Spy() {
        val spy : Cache = spyk(cache)
        every { spy.now() } returns 0L
        spy.set(cacheKey)

        clearAllMocks()
        Assertions.assertThat(spy.isCached(cacheKey)).isFalse()
    }*/

    @Test
    fun isCached_success() {
        every { preferences.cacheExpirationTime } returns 60000
        cache.set(cacheKey)
        Assertions.assertThat(cache.isCached(cacheKey)).isTrue()
    }
}