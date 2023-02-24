package com.carles.hyrule.data

import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleRepositoryAltTest {

    private val factory: HyruleDatasourceFactoryAlt = mockk()
    private val datasource: HyruleDatasource = mockk()
    private lateinit var repository: HyruleRepositoryAlt

    @Before
    fun setup() {
        repository = HyruleRepositoryAlt(factory)
    }

    @Test
    fun `given getMonsters, then use factory to get proper class to get them`() {
        every { factory.provideDatasource(any()) } returns datasource
        every { datasource.getMonsters() } returns Single.just(MONSTERS)
        val observer = repository.getMonsters().test()

        verify { factory.provideDatasource(CacheKey(CacheItems.MONSTERS)) }
        verify { datasource.getMonsters() }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given refreshMonsters, then use factory to get proper class to get them`() {
        every { factory.provideDatasource(any(), any()) } returns datasource
        every { datasource.getMonsters() } returns Single.just(MONSTERS)
        val observer = repository.refreshMonsters().test()

        verify { factory.provideDatasource(CacheKey(CacheItems.MONSTERS), true) }
        verify { datasource.getMonsters() }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, then use factory to get proper class to get it`() {
        every { factory.provideDatasource(any()) } returns datasource
        every { datasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = repository.getMonsterDetail(1).test()

        verify { factory.provideDatasource(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        verify { datasource.getMonsterDetail(1) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    @Test
    fun `given refreshMonsterDetail, then use factory to get proper class to get it`() {
        every { factory.provideDatasource(any(), any()) } returns datasource
        every { datasource.getMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = repository.refreshMonsterDetail(1).test()

        verify { factory.provideDatasource(CacheKey(CacheItems.MONSTER_DETAIL, 1), true) }
        verify { datasource.getMonsterDetail(1) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    companion object {
        private val MONSTERS = listOf(Monster(1, "Monster"))
        private val MONSTER_DETAIL = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
    }
}