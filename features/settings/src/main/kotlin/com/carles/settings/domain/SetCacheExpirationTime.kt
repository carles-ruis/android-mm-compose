package com.carles.settings.domain

import com.carles.common.domain.AppSchedulers
import com.carles.settings.data.SettingsRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetCacheExpirationTime @Inject constructor(
    private val repository: SettingsRepository,
    private val schedulers: AppSchedulers
) {

    fun execute(expirationTime: Int): Completable {
        return repository.setCacheExpirationTime(expirationTime)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}