package com.carles.hyrule.data

import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import io.reactivex.Single
import javax.inject.Inject

class HyruleRepositoryAlt @Inject constructor(private val factory: HyruleDatasourceFactoryAlt) : HyruleRepo {

    override fun getMonsters(): Single<List<Monster>> {
        return factory.provideDatasource(CacheKey(CacheItems.MONSTERS)).getMonsters()
    }

    override fun refreshMonsters(): Single<List<Monster>> {
        return factory.provideDatasource(CacheKey(CacheItems.MONSTERS), refresh = true).getMonsters()
    }

    override fun getMonsterDetail(id: Int): Single<MonsterDetail> {
        return factory.provideDatasource(CacheKey(CacheItems.MONSTER_DETAIL, id)).getMonsterDetail(id)
    }

    override fun refreshMonsterDetail(id: Int): Single<MonsterDetail> {
        return factory.provideDatasource(CacheKey(CacheItems.MONSTER_DETAIL, id), refresh = true).getMonsterDetail(id)
    }
}