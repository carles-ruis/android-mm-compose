package com.carles.hyrule.data.local

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.hyrule.Monster
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleLocalDatasourceAltTest {

    private val dao: MonsterDao = mockk()
    private val monstersMapper: MonstersMapper = mockk()
    private val monsterDetailMapper: MonsterDetailMapper = mockk()
    private val cache: Cache = mockk()
    private lateinit var datasource: HyruleLocalDatasourceAlt

    @Before
    fun setup() {
        datasource = HyruleLocalDatasourceAlt(dao, monstersMapper, monsterDetailMapper, cache)
    }

    @Test
    fun `given getMonsters, when monsters are loaded, then map them to model`() {
        every { dao.loadMonsters() } returns Single.just(MONSTERS_ENTITY)
        every { monstersMapper.fromEntity(any()) } returns MONSTERS
        val observer = datasource.getMonsters().test()

        verify { dao.loadMonsters() }
        verify { monstersMapper.fromEntity(MONSTERS_ENTITY) }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, when monster is loaded, then map it to model`() {
        every { dao.loadMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL_ENTITY)
        every { monsterDetailMapper.fromEntity(any()) } returns MONSTER_DETAIL
        val observer = datasource.getMonsterDetail(1).test()

        verify { dao.loadMonsterDetail(1) }
        verify { monsterDetailMapper.fromEntity(MONSTER_DETAIL_ENTITY) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    @Test
    fun `given persist, when monsters are provided, then insert them to database and update cache`() {
        every { dao.deleteMonsters() } returns 1
        every { dao.insertMonsters(any()) } returns listOf(1)
        every { cache.set(any()) } just runs
        val observer = datasource.persist(MONSTERS_ENTITY).test()

        verify { dao.deleteMonsters() }
        verify { dao.insertMonsters(MONSTERS_ENTITY) }
        verify { cache.set(CacheKey(CacheItems.MONSTERS)) }
        observer.assertComplete()
    }

    @Test
    fun `given persist, when monster is provided, then insert it to database and update cache`() {
        every { dao.insertMonsterDetail(any()) } returns 1
        every { cache.set(any()) } just runs
        val observer = datasource.persist(MONSTER_DETAIL_ENTITY).test()

        verify { dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY) }
        verify { cache.set(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        observer.assertComplete()
    }

    companion object {
        private val MONSTERS_ENTITY = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL_ENTITY = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
        private val MONSTERS = listOf(Monster(1, "Monster"))
        private val MONSTER_DETAIL = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
    }
}