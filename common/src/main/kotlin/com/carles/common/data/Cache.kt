package com.carles.common.data

import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

data class CacheKey(val cacheItem: CacheItems, val itemId: Int = 0)

enum class CacheItems { MONSTERS, MONSTER_DETAIL }

@Singleton
class Cache @Inject constructor(private val datastore: AppDatastore) {

    private val map: MutableMap<CacheKey, Long> = mutableMapOf()

    fun isCached(key: CacheKey): Boolean {
        val expiration = map[key] ?: 0L
        if (expiration < now()) {
            map.remove(key)
        }
        return map.containsKey(key)
    }

    @SuppressWarnings("MagicNumber")
    suspend fun set(key: CacheKey) {
        val expirationTime = datastore.getCacheExpirationTime().first()
        map[key] = now() + 1000 * 60 * expirationTime
    }

    suspend fun resetCacheExpiration() {
        map.keys.forEach { key ->
            set(key)
        }
    }

    private fun now() = Calendar.getInstance().timeInMillis
}

object ItemNotCachedException : RuntimeException()