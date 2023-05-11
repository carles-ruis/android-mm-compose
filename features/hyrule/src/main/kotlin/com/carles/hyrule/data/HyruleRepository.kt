package com.carles.hyrule.data

import com.carles.common.data.ItemNotCachedException
import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.local.HyruleLocalDatasource
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import com.carles.hyrule.data.remote.HyruleRemoteDatasource
import javax.inject.Inject

class HyruleRepository @Inject constructor(
    private val localDatasource: HyruleLocalDatasource,
    private val remoteDatasource: HyruleRemoteDatasource,
    private val monstersMapper: MonstersMapper,
    private val monsterDetailMapper: MonsterDetailMapper
) : HyruleRepo {

    override suspend fun getMonsters(): List<Monster> {
        return try {
            val entities = localDatasource.getMonsters()
            monstersMapper.fromEntity(entities)
        } catch (e: ItemNotCachedException) {
            refreshMonsters()
        }
    }

    override suspend fun refreshMonsters(): List<Monster> {
        val dtos = remoteDatasource.getMonsters()
        val entities = monstersMapper.toEntity(dtos)
        localDatasource.persist(entities)
        val localEntities = localDatasource.getMonsters()
        return monstersMapper.fromEntity(localEntities)
    }

    override suspend fun getMonsterDetail(id: Int): MonsterDetail {
        return try {
            val entity = localDatasource.getMonsterDetail(id)
            monsterDetailMapper.fromEntity(entity)
        } catch (e: ItemNotCachedException) {
            refreshMonsterDetail(id)
        }
    }

    override suspend fun refreshMonsterDetail(id: Int): MonsterDetail {
        val dto = remoteDatasource.getMonsterDetail(id)
        val entity = monsterDetailMapper.toEntity(dto)
        localDatasource.persist(entity)
        val localEntity = localDatasource.getMonsterDetail(id)
        return monsterDetailMapper.fromEntity(localEntity)
    }
}