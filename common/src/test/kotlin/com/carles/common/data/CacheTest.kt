package com.carles.common.data

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CacheTest {

    private val preferences: AppPreferences = mockk()
    private val cacheKey = CacheKey(CacheItems.MONSTERS)
    private lateinit var cache: Cache

    @Before
    fun setup() {
        cache = Cache(preferences)
    }

    @Test
    fun `given isCached, when no item set, then it returns false`() {
        every { preferences.cacheExpirationTime } returns 60_000L
        assertFalse(cache.isCached(cacheKey))
    }

    @Test
    fun `given isCached, when item set and expired, then it returns false`() {
        every { preferences.cacheExpirationTime } returns -10_000L
        cache.set(cacheKey)
        assertFalse(cache.isCached(cacheKey))
    }

    @Test
    fun `given isCached, When item is set and not expired, then it returns true`() {
        every { preferences.cacheExpirationTime } returns 60_000L
        cache.set(cacheKey)
        assertTrue(cache.isCached(cacheKey))
    }
}