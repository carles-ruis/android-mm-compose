package com.carles.common.data

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.Test

class CacheTest {

    private val preferences: AppPreferences = mockk(relaxed = true)
    private val cache = Cache(preferences)
    private val cacheKey = CacheKey(CacheItems.POI_LIST)

    @Test
    fun isCached_itemNotCached() {
        Assertions.assertThat(cache.isCached(cacheKey)).isFalse
    }

    @Test
    fun isCached_itemExpired() {
        every { preferences.cacheExpirationTime } returns 0
        cache.set(cacheKey)
        Assertions.assertThat(cache.isCached(cacheKey)).isFalse
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
        every { preferences.cacheExpirationTime } returns 60_000L
        cache.set(cacheKey)
        Assertions.assertThat(cache.isCached(cacheKey)).isTrue
    }
}