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
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
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
    fun `given getMonsters, when monsters are cached, then get monsters from local`() = runTest {
        coEvery { localDatasource.getMonsters() } returns MONSTERS_ENTITY
        coEvery { monstersMapper.fromEntity(any()) } returns MONSTERS
        val result = repository.getMonsters()

        coVerify { localDatasource.getMonsters() }
        coVerify { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        assertEquals(MONSTERS, result)
    }

    @Test
    fun `given getMonsters, when monsters are not cached, then refresh them from remote`() = runTest {
        coEvery { localDatasource.getMonsters() } throws ItemNotCachedException
        coEvery { monstersMapper.fromEntity(any()) } returns MONSTERS
        coEvery { spy.refreshMonsters() } returns MONSTERS
        val result = spy.getMonsters()

        coVerify { localDatasource.getMonsters() }
        coVerify(exactly = 0) { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        coVerify { spy.refreshMonsters() }
        assertEquals(MONSTERS, result)
    }

    @Test
    fun `given refreshMonsters, when monsters are obtained, then persist them and get them from local`() = runTest {
        coEvery { remoteDatasource.getMonsters() } returns MONSTERS_DTO
        coEvery { monstersMapper.toEntity(any()) } returns MONSTERS_ENTITY
        coEvery { localDatasource.persist(any<List<MonsterEntity>>()) } just Runs
        coEvery { localDatasource.getMonsters() } returns MONSTERS_ENTITY
        coEvery { monstersMapper.fromEntity(any()) } returns MONSTERS
        val result = repository.refreshMonsters()

        coVerify { remoteDatasource.getMonsters() }
        coVerify { monstersMapper.toEntity(MONSTERS_DTO) }
        coVerify { localDatasource.persist(MONSTERS_ENTITY) }
        coVerify { localDatasource.getMonsters() }
        coVerify { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        assertEquals(MONSTERS, result)
    }

    @Test
    fun `given getMonsterDetail, when monster is cached, then get monster from local`() = runTest {
        coEvery { localDatasource.getMonsterDetail(any()) } returns MONSTER_DETAIL_ENTITY
        coEvery { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        val result = repository.getMonsterDetail(1)

        coVerify { localDatasource.getMonsterDetail(1) }
        coVerify { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        assertEquals(MONSTER_DETAIL, result)
    }

    @Test
    fun `given getMonsterDetail, when monster is not cached, then refresh it from remote`() = runTest {
        coEvery { localDatasource.getMonsterDetail(any()) } throws ItemNotCachedException
        coEvery { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        coEvery { spy.refreshMonsterDetail(any()) } returns MONSTER_DETAIL
        val result = spy.getMonsterDetail(1)

        coVerify { localDatasource.getMonsterDetail(1) }
        coVerify(exactly = 0) { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        coVerify { spy.refreshMonsterDetail(1) }
        assertEquals(MONSTER_DETAIL, result)
    }

    @Test
    fun `given refreshMonsterDetail, when monster is obtained, then persist it and get it from local`() = runTest {
        coEvery { remoteDatasource.getMonsterDetail(any()) } returns MONSTER_DETAIL_DTO
        coEvery { monsterDetailMapper.toEntity(any()) } returns MONSTER_DETAIL_ENTITY
        coEvery { localDatasource.persist(any<MonsterDetailEntity>()) } just Runs
        coEvery { localDatasource.getMonsterDetail(any()) } returns MONSTER_DETAIL_ENTITY
        coEvery { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        val result = repository.refreshMonsterDetail(1)

        coVerify { remoteDatasource.getMonsterDetail(1) }
        coVerify { monsterDetailMapper.toEntity(MONSTER_DETAIL_DTO) }
        coVerify { localDatasource.persist(MONSTER_DETAIL_ENTITY) }
        coVerify { localDatasource.getMonsterDetail(1) }
        coVerify { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        assertEquals(MONSTER_DETAIL, result)
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