package com.carles.settings.domain

import com.carles.core.domain.AppSchedulers
import com.carles.settings.data.SettingsRepository
import io.reactivex.Completable

class UpdateCacheExpirationUsecase(private val repository: SettingsRepository, private val schedulers: AppSchedulers) {

    operator fun invoke(): Completable =
        repository.updateCacheExpiration().subscribeOn(schedulers.io).observeOn(schedulers.ui)
}