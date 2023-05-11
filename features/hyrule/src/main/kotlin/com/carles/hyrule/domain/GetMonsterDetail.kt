package com.carles.hyrule.domain

import com.carles.common.domain.AppDispatchers
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.HyruleRepo
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMonsterDetail @Inject constructor(
    private val repository: HyruleRepo,
    private val dispatchers: AppDispatchers
) {

    suspend fun execute(id: Int): MonsterDetail {
        return withContext(dispatchers.io) {
            repository.getMonsterDetail(id)
        }
    }
}