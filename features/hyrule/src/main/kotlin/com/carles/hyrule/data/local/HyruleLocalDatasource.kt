package com.carles.hyrule.data.local

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.common.data.ItemNotCachedException
import io.reactivex.Completable
import io.reactivex.Single

class HyruleLocalDatasource(
    private val dao: MonsterDao,
    private val cache: Cache,
) {

    fun getMonsters(): Single<List<MonsterEntity>> {
        return if (cache.isCached(CacheKey(CacheItems.MONSTERS))) {
            dao.loadMonsters()
        } else {
            Single.error(ItemNotCachedException)
        }
    }

    fun getMonsterDetail(id: Int): Single<MonsterDetailEntity> {
        return if (cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, id))) {
            dao.loadMonsterDetail(id)
        } else {
            Single.error(ItemNotCachedException)
        }
    }

    fun persist(monsters: List<MonsterEntity>): Completable {
        return Completable.fromAction {
            dao.deleteMonsters()
            dao.insertMonsters(monsters)
            cache.set(CacheKey(CacheItems.MONSTERS))
        }
    }

    fun persist(monster: MonsterDetailEntity): Completable {
        return Completable.fromAction {
            dao.insertMonsterDetail(monster)
            cache.set(CacheKey(CacheItems.MONSTER_DETAIL, monster.id))
        }
    }
}