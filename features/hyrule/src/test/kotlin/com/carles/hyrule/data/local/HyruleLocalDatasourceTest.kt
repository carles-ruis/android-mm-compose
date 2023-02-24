package com.carles.hyrule.data.local

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.common.data.ItemNotCachedException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HyruleLocalDatasourceTest {

    private val dao: MonsterDao = mockk()
    private val cache: Cache = mockk()
    private lateinit var datasource: HyruleLocalDatasource

    @Before
    fun setup() {
        datasource = HyruleLocalDatasource(dao, cache)
    }

    @Test
    fun `given getMonsters, when items are cached, then dao loads them`() {
        every { cache.isCached(any()) } returns true
        every { dao.loadMonsters() } returns Single.just(MONSTERS)
        val observer = datasource.getMonsters().test()

        verify { cache.isCached(CacheKey(CacheItems.MONSTERS)) }
        verify { dao.loadMonsters() }
        observer.assertValue(MONSTERS)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsters, when items are not cached, then emit ItemNotCachedException`() {
        every { cache.isCached(any()) } returns false
        val observer = datasource.getMonsters().test()

        verify { cache.isCached(CacheKey(CacheItems.MONSTERS)) }
        verify(exactly = 0) { dao.loadMonsters() }
        observer.assertNoValues()
        observer.assertError(ItemNotCachedException)
    }

    @Test
    fun `given getMonsterDetail, when item is cached, then dao loads it`() {
        every { cache.isCached(any()) } returns true
        every { dao.loadMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = datasource.getMonsterDetail(1).test()

        verify { cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        verify { dao.loadMonsterDetail(1) }
        observer.assertValue(MONSTER_DETAIL)
        observer.assertComplete()
    }

    @Test
    fun `given getMonsterDetail, when item is not cached, then emit ItemNotCachedException`() {
        every { cache.isCached(any()) } returns false
        every { dao.loadMonsterDetail(any()) } returns Single.just(MONSTER_DETAIL)
        val observer = datasource.getMonsterDetail(1).test()

        verify { cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        verify(exactly = 0) { dao.loadMonsterDetail(any()) }
        observer.assertError(ItemNotCachedException)
        observer.assertNoValues()
    }

    companion object {
        private val MONSTERS = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
    }
}