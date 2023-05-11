package com.carles.hyrule.data.local

import com.carles.common.data.Cache
import com.carles.common.data.CacheItems
import com.carles.common.data.CacheKey
import com.carles.common.data.ItemNotCachedException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HyruleLocalDatasourceTest {

    private val dao: MonsterDao = mockk()
    private val cache: Cache = mockk()
    private lateinit var datasource: HyruleLocalDatasource

    @Before
    fun setup() {
        datasource = HyruleLocalDatasource(dao, cache)
    }

    @Test
    fun `given getMonsters, when items are cached, then dao loads them`() = runTest {
        coEvery { cache.isCached(any()) } returns true
        coEvery { dao.loadMonsters() } returns MONSTERS
        val result = datasource.getMonsters()

        coVerify { cache.isCached(CacheKey(CacheItems.MONSTERS)) }
        coVerify { dao.loadMonsters() }
        assertEquals(MONSTERS, result)
    }

    @Test(expected = ItemNotCachedException::class)
    fun `given getMonsters, when items are not cached, then throw ItemNotCachedException`() = runTest {
        coEvery { cache.isCached(any()) } returns false
        datasource.getMonsters()

        coVerify { cache.isCached(CacheKey(CacheItems.MONSTERS)) }
        coVerify(exactly = 0) { dao.loadMonsters() }
    }

    @Test
    fun `given getMonsterDetail, when item is cached, then dao loads it`() = runTest {
        coEvery { cache.isCached(any()) } returns true
        coEvery { dao.loadMonsterDetail(any()) } returns MONSTER_DETAIL
        val result = datasource.getMonsterDetail(1)

        coVerify { cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        coVerify { dao.loadMonsterDetail(1) }
        assertEquals(MONSTER_DETAIL, result)
    }

    @Test(expected = ItemNotCachedException::class)
    fun `given getMonsterDetail, when item is not cached, then emit ItemNotCachedException`() = runTest {
        coEvery { cache.isCached(any()) } returns false
        coEvery { dao.loadMonsterDetail(any()) } returns MONSTER_DETAIL
        datasource.getMonsterDetail(1)

        coVerify { cache.isCached(CacheKey(CacheItems.MONSTER_DETAIL, 1)) }
        coVerify(exactly = 0) { dao.loadMonsterDetail(any()) }
    }

    companion object {
        private val MONSTERS = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
    }
}