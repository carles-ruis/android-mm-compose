package com.carles.hyrule.data

import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail

interface HyruleRepo {

    suspend fun getMonsters(): List<Monster>

    suspend fun refreshMonsters(): List<Monster>

    suspend fun getMonsterDetail(id: Int): MonsterDetail

    suspend fun refreshMonsterDetail(id: Int): MonsterDetail
}