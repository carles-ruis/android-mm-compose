package com.carles.core.data

import java.util.*

data class CacheKey(val cacheItem: CacheItems, val itemId: String = "")

enum class CacheItems { POI_LIST, POI_DETAIL }

class Cache(private val preferences: AppPreferences) {

    private val map: MutableMap<CacheKey, Long> = mutableMapOf()

    fun isCached(key: CacheKey): Boolean {
        if (map.containsKey(key) && map.get(key) ?: 0L < now()) {
            map.remove(key)
        }
        return map.containsKey(key)
    }

    @SuppressWarnings("MagicNumber")
    fun set(key: CacheKey) {
        map.set(key, now() + preferences.cacheExpirationTime * 60 * 1000)
    }

    fun updateCacheExpiration() {
        for (key in map.keys) {
            set(key)
        }
    }

    private fun now() = Calendar.getInstance().timeInMillis
}

object ItemNotCachedException : RuntimeException()