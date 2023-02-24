package com.carles.hyrule.data

import com.carles.common.data.ItemNotCachedException
import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.local.HyruleLocalDatasource
import com.carles.hyrule.data.local.MonsterDetailEntity
import com.carles.hyrule.data.local.MonsterEntity
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import com.carles.hyrule.data.remote.HyruleRemoteDatasource
import com.carles.hyrule.data.remote.MonsterDetailDto
import com.carles.hyrule.data.remote.MonsterDetailResponseDto
import com.carles.hyrule.data.remote.MonsterDto
import com.carles.hyrule.data.remote.MonstersResponseDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleRepositoryTest {

    private val localDatasource: HyruleLocalDatasource = mockk()
    private val remoteDatasource: HyruleRemoteDatasource = mockk()
    private val monstersMapper: MonstersMapper = mockk()
    private val monsterDetailMapper: MonsterDetailMapper = mockk()
    private lateinit var repository: HyruleRepository
    private lateinit var spy: HyruleRepository

    @Before
    fun setup() {
        repository = HyruleRepository(localDatasource, remoteDatasource, monstersMapper, monsterDetailMapper)
        spy = spyk(HyruleRepository(localDatasource, remoteDatasource, monstersMapper, monsterDetailMapper))
    }

    @Test
    fun `given getMonsters, when monsters are cached, then get monsters from local`() {
        every { localDatasource.getMonsters() } returns Single.just(MONSTERS_ENTITY)
        every { monstersMapper.fromEntity(any()) } returns MONSTERS
        val observer = repository.getMonsters().test()

        verify { localDatasource.getMonsters() }
        verify { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsters, when monsters are not cached, then refresh them from remote`() {
        every { localDatasource.getMonsters() } returns Single.error(ItemNotCachedException)
        every { monstersMapper.fromEntity(any()) } returns MONSTERS
        every { spy.refreshMonsters() } returns Single.just(MONSTERS)
        val observer = spy.getMonsters().test()

        verify { localDatasource.getMonsters() }
        verify(exactly = 0) { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        verify { spy.refreshMonsters() }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given refreshMonsters, when monsters are obtained, then persist them and get them from local`() {
        every { remoteDatasource.getMonsters() } returns Single.just(MONSTERS_DTO)
        every { monstersMapper.toEntity(any()) } returns MONSTERS_ENTITY
        every { localDatasource.persist(any<List<MonsterEntity>>()) } returns Completable.complete()
        every { localDatasource.getMonsters() } returns Single.just(MONSTERS_ENTITY)
        every { monstersMapper.fromEntity(any()) } returns MONSTERS
        val observer = repository.refreshMonsters().test()

        verify { remoteDatasource.getMonsters() }
        verify { monstersMapper.toEntity(MONSTERS_DTO) }
        verify { localDatasource.persist(MONSTERS_ENTITY) }
        verify { localDatasource.getMonsters() }
        verify { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, when monster is cached, then get monster from local`() {
        every { localDatasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_ENTITY)
        every { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        val observer = repository.getMonsterDetail(1).test()

        verify { localDatasource.getMonsterDetail(1) }
        verify { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, when monster is not cached, then refresh it from remote`() {
        every { localDatasource.getMonsterDetail(any()) } returns Single.error(ItemNotCachedException)
        every { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        every { spy.refreshMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = spy.getMonsterDetail(1).test()

        verify { localDatasource.getMonsterDetail(1) }
        verify(exactly = 0) { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        verify { spy.refreshMonsterDetail(1) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    @Test
    fun `given refreshMonsterDetail, when monster is obtained, then persist it and get it from local`() {
        every { remoteDatasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_DTO)
        every { monsterDetailMapper.toEntity(any()) } returns MONSTER_DETAIL_ENTITY
        every { localDatasource.persist(any<MonsterDetailEntity>()) } returns Completable.complete()
        every { localDatasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_ENTITY)
        every { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        val observer = repository.refreshMonsterDetail(1).test()

        verify { remoteDatasource.getMonsterDetail(1) }
        verify { monsterDetailMapper.toEntity(MONSTER_DETAIL_DTO) }
        verify { localDatasource.persist(MONSTER_DETAIL_ENTITY) }
        verify { localDatasource.getMonsterDetail(1) }
        verify { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
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