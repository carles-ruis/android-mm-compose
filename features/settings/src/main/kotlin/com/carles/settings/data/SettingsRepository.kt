package com.carles.settings.data

import androidx.annotation.VisibleForTesting
import com.carles.common.data.AppPreferences
import com.carles.common.data.Cache
import com.carles.settings.UserSettings
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val cache: Cache,
    private val appPreferences: AppPreferences
) {

    private val userSettingsSubject: BehaviorSubject<UserSettings> by lazy {
        BehaviorSubject.createDefault(getUserSettings())
    }

    fun setCacheExpirationTime(expirationTime: Int): Completable = Completable.fromAction {
        cache.resetCacheExpiration()
        appPreferences.cacheExpirationTime = expirationTime
        emitUserSettings()
    }

    fun observeUserSettings(): Flowable<UserSettings> {
        return userSettingsSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    @VisibleForTesting
    fun emitUserSettings() {
        userSettingsSubject.onNext(getUserSettings())
    }

    @VisibleForTesting
    fun getUserSettings(): UserSettings {
        return UserSettings(appPreferences.cacheExpirationTime)
    }
}