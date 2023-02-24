package com.carles.hyrule.data.mapper

import com.carles.hyrule.Monster
import com.carles.hyrule.data.local.MonsterEntity
import com.carles.hyrule.data.remote.MonstersResponseDto

class MonstersMapper {

    fun toEntity(dto: MonstersResponseDto): List<MonsterEntity> =
        dto.data.map { MonsterEntity(it.id, it.name) }

    fun fromEntity(entity: List<MonsterEntity>): List<Monster> =
        entity.map { Monster(it.id, it.name) }
}