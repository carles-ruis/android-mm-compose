package com.carles.hyrule.data

import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.local.HyruleLocalDatasource
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import com.carles.hyrule.data.remote.HyruleRemoteDatasource
import io.reactivex.Single

class HyruleRepository(
    private val localDatasource: HyruleLocalDatasource,
    private val remoteDatasource: HyruleRemoteDatasource,
    private val monstersMapper: MonstersMapper,
    private val monsterDetailMapper: MonsterDetailMapper
) : HyruleRepo {

    override fun getMonsters(): Single<List<Monster>> {
        return localDatasource.getMonsters().map { entity ->
            monstersMapper.fromEntity(entity)
        }.onErrorResumeNext {
            refreshMonsters()
        }
    }

    override fun refreshMonsters(): Single<List<Monster>> {
        return remoteDatasource.getMonsters().map { dto ->
            monstersMapper.toEntity(dto)
        }.flatMap {
            localDatasource.persist(it).andThen(
                Single.defer {
                    localDatasource.getMonsters().map { entity ->
                        monstersMapper.fromEntity(entity)
                    }
                }
            )
        }
    }

    override fun getMonsterDetail(id: Int): Single<MonsterDetail> {
        return localDatasource.getMonsterDetail(id).map { entity ->
            monsterDetailMapper.fromEntity(entity)
        }.onErrorResumeNext {
            refreshMonsterDetail(id)
        }
    }

    override fun refreshMonsterDetail(id: Int): Single<MonsterDetail> {
        return remoteDatasource.getMonsterDetail(id).map { dto ->
            monsterDetailMapper.toEntity(dto)
        }.flatMap {
            localDatasource.persist(it).andThen(
                Single.defer {
                    localDatasource.getMonsterDetail(id).map { entity ->
                        monsterDetailMapper.fromEntity(entity)
                    }
                }
            )
        }
    }
}