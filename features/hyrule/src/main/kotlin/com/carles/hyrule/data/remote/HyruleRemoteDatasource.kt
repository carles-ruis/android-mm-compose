package com.carles.hyrule.data.remote

import javax.inject.Inject

class HyruleRemoteDatasource @Inject constructor(private val api: HyruleApi) {

    suspend fun getMonsters(): MonstersResponseDto {
        return api.getMonsters()
    }

    suspend fun getMonsterDetail(id: Int): MonsterDetailResponseDto {
        return api.getMonsterDetail(id.toString())
    }
}