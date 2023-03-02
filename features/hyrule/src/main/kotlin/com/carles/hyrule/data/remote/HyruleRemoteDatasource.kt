package com.carles.hyrule.data.remote

import io.reactivex.Single

class HyruleRemoteDatasource(private val api: HyruleApi) {

    fun getMonsters(): Single<MonstersResponseDto> {
        return api.getMonsters()
    }

    fun getMonsterDetail(id: Int): Single<MonsterDetailResponseDto> {
        return api.getMonsterDetail(id.toString())
    }
}