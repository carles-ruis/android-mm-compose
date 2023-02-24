package com.carles.hyrule.data

import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import io.reactivex.Single

interface HyruleRepo {

    fun getMonsters(): Single<List<Monster>>

    fun refreshMonsters(): Single<List<Monster>>

    fun getMonsterDetail(id: Int): Single<MonsterDetail>

    fun refreshMonsterDetail(id: Int): Single<MonsterDetail>
}