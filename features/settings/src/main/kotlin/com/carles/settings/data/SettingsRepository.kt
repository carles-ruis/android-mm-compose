package com.carles.settings.data

import com.carles.common.data.AppDatastore
import com.carles.common.data.Cache
import com.carles.settings.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val cache: Cache,
    private val appDatastore: AppDatastore
) {

    suspend fun setCacheExpirationTime(expirationTime: Int) {
        cache.resetCacheExpiration()
        appDatastore.setCacheExpirationTime(expirationTime)
    }

    fun observeUserSettings(): Flow<UserSettings> {
        return appDatastore.getCacheExpirationTime().map { expirationTime ->
            UserSettings(expirationTime)
        }
    }
}