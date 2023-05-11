package com.carles.mm

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.local.MonsterDao
import com.carles.hyrule.data.local.MonsterDetailEntity
import com.carles.hyrule.data.local.MonsterEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MonsterDaoTest {

    private lateinit var dao: MonsterDao
    private lateinit var database: HyruleDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = InstrumentationRegistry.getInstrumentation().context,
            klass = HyruleDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.monsterDao()
    }

    @After
    @kotlin.jvm.Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenLoadMonsters_whenCalled_thenLoadAllMonstersFromDatabase() = runTest {
        dao.insertMonsters(MONSTERS_ENTITY)
        val result = dao.loadMonsters()
        assertEquals(MONSTERS_ENTITY, result)
    }

    @Test
    fun givenInsertMonsters_whenMonsterIsNotInDatabase_thenInsertIt() = runTest {
        assertEquals(listOf(1L), dao.insertMonsters(MONSTERS_ENTITY))
    }

    @Test
    fun givenInsertMonsters_whenMonsterIsAlreadyInDatabase_thenReplaceIt() = runTest {
        dao.insertMonsters(MONSTERS_ENTITY)
        assertEquals(listOf(1L), dao.insertMonsters(MONSTERS_ENTITY))
    }

    @Test
    fun givenDeleteMonsters_whenCalled_thenDeleteAllMonstersFromDatabase() = runTest {
        dao.insertMonsters(MONSTERS_ENTITY)
        assertEquals(1, dao.deleteMonsters())
    }

    @Test
    fun givenLoadMonsterDetail_whenCalled_thenGetProperMonster() = runTest {
        dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY)
        assertEquals(MONSTER_DETAIL_ENTITY, dao.loadMonsterDetail(1))
    }

    @Test
    fun givenInsertMonsterDetail_whenMonsterIsNotInDatabase_thenInsertIt() = runTest {
        assertEquals(1L, dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY))
    }

    @Test
    fun givenInsertMonsterDetail_whenMonsterIsAlreadyInDatabase_thenReplaceIt() = runTest {
        dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY)
        assertEquals(1, dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY))
    }

    companion object {
        private val MONSTERS_ENTITY = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL_ENTITY = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
    }
}