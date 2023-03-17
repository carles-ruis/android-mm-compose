package com.carles.settings.domain

import com.carles.common.domain.AppSchedulers
import com.carles.settings.UserSettings
import com.carles.settings.data.SettingsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ObserveUserSettings @Inject constructor(
    private val repository: SettingsRepository,
    private val schedulers: AppSchedulers
) {

    fun execute(): Flowable<UserSettings> {
        return repository.observeUserSettings()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}