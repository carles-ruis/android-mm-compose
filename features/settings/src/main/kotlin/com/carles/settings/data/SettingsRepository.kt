package com.carles.settings.data

import com.carles.common.data.Cache
import io.reactivex.Completable

class SettingsRepository(private val cache: Cache) {

    fun resetCacheExpiration(): Completable = Completable.fromAction {
        cache.resetCacheExpiration()
    }
}