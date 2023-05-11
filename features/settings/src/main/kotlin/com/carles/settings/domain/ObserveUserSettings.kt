package com.carles.settings.domain

import com.carles.common.domain.AppDispatchers
import com.carles.settings.UserSettings
import com.carles.settings.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveUserSettings @Inject constructor(
    private val repository: SettingsRepository,
    private val dispatchers: AppDispatchers
) {

    fun execute(): Flow<UserSettings> {
        return repository.observeUserSettings().flowOn(dispatchers.io)
    }
}