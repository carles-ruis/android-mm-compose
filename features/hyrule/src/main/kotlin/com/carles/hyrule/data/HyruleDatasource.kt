package com.carles.hyrule.data

import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import io.reactivex.Single

interface HyruleDatasource {

    fun getMonsters(): Single<List<Monster>>

    fun getMonsterDetail(id: Int): Single<MonsterDetail>
}