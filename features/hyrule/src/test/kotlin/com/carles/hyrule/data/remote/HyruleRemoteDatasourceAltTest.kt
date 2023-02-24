package com.carles.hyrule.data.remote

import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.local.HyruleLocalDatasourceAlt
import com.carles.hyrule.data.local.MonsterDetailEntity
import com.carles.hyrule.data.local.MonsterEntity
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleRemoteDatasourceAltTest {

    private val api: HyruleApi = mockk()
    private val localDatasource: HyruleLocalDatasourceAlt = mockk()
    private val monstersMapper: MonstersMapper = mockk()
    private val monsterDetailMapper: MonsterDetailMapper = mockk()
    private lateinit var datasource: HyruleRemoteDatasourceAlt

    @Before
    fun setup() {
        datasource = HyruleRemoteDatasourceAlt(api, localDatasource, monstersMapper, monsterDetailMapper)
    }

    @Test
    fun `given getMonsters, when monsters are obtained, then persist to and get them from database`() {
        every { api.getMonsters() } returns Single.just(MONSTERS_DTO)
        every { monstersMapper.toEntity(any()) } returns MONSTERS_ENTITY
        every { localDatasource.persist(any<List<MonsterEntity>>()) } returns Completable.complete()
        every { localDatasource.getMonsters() } returns Single.just(MONSTERS)
        val observer = datasource.getMonsters().test()

        verify { api.getMonsters() }
        verify { monstersMapper.toEntity(MONSTERS_DTO) }
        verify { localDatasource.persist(MONSTERS_ENTITY) }
        verify { localDatasource.getMonsters() }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, when monster is obtained, then persist it to and get it from database`() {
        every { api.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_DTO)
        every { monsterDetailMapper.toEntity(any()) } returns MONSTER_DETAIL_ENTITY
        every { localDatasource.persist(any<MonsterDetailEntity>()) } returns Completable.complete()
        every { localDatasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = datasource.getMonsterDetail(1).test()

        verify { api.getMonsterDetail("1") }
        verify { monsterDetailMapper.toEntity(MONSTER_DETAIL_DTO) }
        verify { localDatasource.persist(MONSTER_DETAIL_ENTITY) }
        verify { localDatasource.getMonsterDetail(1) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    companion object {
        private val MONSTERS = listOf(Monster(1, "Monster"))
        private val MONSTER_DETAIL = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
        private val MONSTERS_ENTITY = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL_ENTITY = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
        private val MONSTERS_DTO = MonstersResponseDto(listOf(MonsterDto(1, "Monster")))
        private val MONSTER_DETAIL_DTO = MonsterDetailResponseDto(
            MonsterDetailDto(
                id = 1,
                name = "Monster",
                commonLocations = listOf("Here"),
                description = "big monster",
                image = "https://url",
                category = "monster"
            )
        )
    }
}