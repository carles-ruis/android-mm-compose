package com.carles.settings.data

import com.carles.common.data.Cache
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(private val cache: Cache) {

    fun resetCacheExpiration(): Completable = Completable.fromAction {
        cache.resetCacheExpiration()
    }
}