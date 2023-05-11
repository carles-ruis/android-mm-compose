package com.carles.hyrule.data.remote

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HyruleRemoteDatasourceTest {

    private val api: HyruleApi = mockk()
    private lateinit var datasource: HyruleRemoteDatasource

    @Before
    fun setup() {
        datasource = HyruleRemoteDatasource(api)
    }

    @Test
    fun `given getMonsters, then get monsters from api`() = runTest {
        coEvery { api.getMonsters() } returns MONSTERS_DTO
        val result = datasource.getMonsters()

        coVerify { api.getMonsters() }
        assertEquals(MONSTERS_DTO, result)
    }

    @Test
    fun `given getMonsterDetail, then get monster detail from api`() = runTest {
        coEvery { api.getMonsterDetail(any()) } returns MONSTER_DETAIL_DTO
        val result = datasource.getMonsterDetail(1)

        coVerify { api.getMonsterDetail("1") }
        assertEquals(MONSTER_DETAIL_DTO, result)
    }

    companion object {
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