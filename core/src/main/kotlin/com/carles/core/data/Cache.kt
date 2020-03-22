package com.carles.core.data

import java.util.*

data class CacheKey(val cacheItem: CacheItems, val itemId: String = "")

enum class CacheItems { POI_LIST, POI_DETAIL }

class Cache {

    companion object {
        private const val EXPIRE_TIME = 1000 * 60
    }

    private val map: MutableMap<CacheKey, Long> = mutableMapOf()

    fun isCached(key: CacheKey): Boolean {
        if (map.containsKey(key) && map.get(key) ?: 0L < now()) {
            map.remove(key)
        }
        return map.containsKey(key)
    }

    fun set(key: CacheKey) {
        map.set(key, now() + EXPIRE_TIME)
    }

    fun now() = Calendar.getInstance().timeInMillis
}

object ItemNotCachedException : RuntimeException()