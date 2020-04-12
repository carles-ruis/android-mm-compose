package com.carles.settings.data

import io.reactivex.Completable

class SettingsRepository(private val datasource: SettingsLocalDatasource) {

    fun updateCacheExpiration(): Completable = datasource.updateCacheExpiration()
}