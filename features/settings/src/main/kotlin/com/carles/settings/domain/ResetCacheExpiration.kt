package com.carles.settings.domain

import com.carles.common.domain.AppSchedulers
import com.carles.settings.data.SettingsRepository
import io.reactivex.Completable

class ResetCacheExpiration(
    private val repository: SettingsRepository,
    private val schedulers: AppSchedulers
) {

    fun execute(): Completable {
        return repository.resetCacheExpiration()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}