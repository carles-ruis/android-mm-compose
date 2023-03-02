package com.carles.mm.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.local.MonsterDao
import com.carles.hyrule.data.local.MonsterDetailEntity
import com.carles.hyrule.data.local.MonsterEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MonsterDaoTest {

    private lateinit var dao: MonsterDao
    private lateinit var database: HyruleDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
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
    fun givenLoadMonsters_whenCalled_thenLoadAllMonstersFromDatabase() {
        dao.insertMonsters(MONSTERS_ENTITY)
        dao.loadMonsters().test().assertValue(MONSTERS_ENTITY)
    }

    @Test
    fun givenInsertMonsters_whenMonsterIsNotInDatabase_thenInsertIt() {
        assertEquals(listOf(1L), dao.insertMonsters(MONSTERS_ENTITY))
    }

    @Test
    fun givenInsertMonsters_whenMonsterIsAlreadyInDatabase_thenReplaceIt() {
        dao.insertMonsters(MONSTERS_ENTITY)
        assertEquals(listOf(1L), dao.insertMonsters(MONSTERS_ENTITY))
    }

    @Test
    fun givenDeleteMonsters_whenCalled_thenDeleteAllMonstersFromDatabase() {
        dao.insertMonsters(MONSTERS_ENTITY)
        assertEquals(1, dao.deleteMonsters())
    }

    @Test
    fun givenLoadMonsterDetail_whenCalled_thenGetProperMonster() {
        dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY)
        dao.loadMonsterDetail(1).test().assertValue(MONSTER_DETAIL_ENTITY)
    }

    @Test
    fun givenInsertMonsterDetail_whenMonsterIsNotInDatabase_thenInsertIt() {
        assertEquals(1L, dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY))
    }

    @Test
    fun givenInsertMonsterDetail_whenMonsterIsAlreadyInDatabase_thenReplaceIt() {
        dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY)
        assertEquals(1L, dao.insertMonsterDetail(MONSTER_DETAIL_ENTITY))
    }

    companion object {
        private val MONSTERS_ENTITY = listOf(MonsterEntity(1, "Monster"))
        private val MONSTER_DETAIL_ENTITY = MonsterDetailEntity(1, "Monster", "Here", "Big monster", "https://url")
    }
}