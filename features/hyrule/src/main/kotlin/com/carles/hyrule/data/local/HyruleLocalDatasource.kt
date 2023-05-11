package com.carles.hyrule.data.local

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.common.data.ItemNotCachedException
import javax.inject.Inject

class HyruleLocalDatasource @Inject constructor(
    private val dao: MonsterDao,
    private val cache: Cache,
) {

    suspend fun getMonsters(): List<MonsterEntity> {
        return if (cache.isCached(CacheKey(CacheItems.MONSTERS))) {
            dao.loadMonsters()
        } else {
            throw ItemNotCachedException
        }
    }

    suspend fun getMonsterDetail(id: Int): MonsterDetailEntity {
        return if (cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, id))) {
            dao.loadMonsterDetail(id)
        } else {
            throw ItemNotCachedException
        }
    }

    suspend fun persist(monsters: List<MonsterEntity>) {
        dao.deleteMonsters()
        dao.insertMonsters(monsters)
        cache.set(CacheKey(CacheItems.MONSTERS))
    }

    suspend fun persist(monster: MonsterDetailEntity) {
        dao.insertMonsterDetail(monster)
        cache.set(CacheKey(CacheItems.MONSTER_DETAIL, monster.id))
    }
}