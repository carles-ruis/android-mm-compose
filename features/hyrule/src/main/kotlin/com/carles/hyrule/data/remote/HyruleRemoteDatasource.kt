package com.carles.hyrule.data.remote

import io.reactivex.Single

class HyruleRemoteDatasource(private val api: HyruleApi) {

    fun getMonsters(): Single<MonstersResponseDto> = api.getMonsters()

    fun getMonsterDetail(id: Int) = api.getMonsterDetail(id.toString())
}