package com.carles.hyrule.domain

import com.carles.common.domain.AppDispatchers
import com.carles.hyrule.Monster
import com.carles.hyrule.data.HyruleRepo
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshMonsters @Inject constructor(
    private val repository: HyruleRepo,
    private val dispatchers: AppDispatchers
) {

    suspend fun execute(): List<Monster> {
        return withContext(dispatchers.io) {
            repository.refreshMonsters()
        }
    }
}