package com.carles.hyrule.data.remote

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleRemoteDatasourceTest {

    private val api: HyruleApi = mockk()
    private lateinit var datasource: HyruleRemoteDatasource

    @Before
    fun setup() {
        datasource = HyruleRemoteDatasource(api)
    }

    @Test
    fun `given getMonsters, then get monsters from api`() {
        every { api.getMonsters() } returns Single.just(MONSTERS_DTO)
        val observer = datasource.getMonsters().test()

        verify { api.getMonsters() }
        observer.assertValue(MONSTERS_DTO)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, then get monster detail from api`() {
        every { api.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_DTO)
        val observer = datasource.getMonsterDetail(1).test()

        verify { api.getMonsterDetail("1") }
        observer.assertValue(MONSTER_DETAIL_DTO)
        observer.assertComplete()
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