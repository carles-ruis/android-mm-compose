package com.carles.settings.domain

import com.carles.common.domain.AppDispatchers
import com.carles.settings.data.SettingsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetCacheExpirationTime @Inject constructor(
    private val repository: SettingsRepository,
    private val dispatchers: AppDispatchers
) {

    suspend fun execute(expirationTime: Int) {
        return withContext(dispatchers.io) {
            repository.setCacheExpirationTime(expirationTime)
        }
    }
}