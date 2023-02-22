package com.carles.settings.data

import com.carles.common.data.Cache
import io.reactivex.Completable

class SettingsLocalDatasource(private val cache: Cache) {

    fun updateCacheExpiration() = Completable.fromCallable {
        cache.updateCacheExpiration()
    }
}