package com.carles.mm.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carles.mm.Poi
import com.carles.mm.PoiDetail
import com.carles.mm.data.PoiDao
import com.carles.mm.data.PoiDatabase
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PoiDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val poiList = listOf(Poi("1", "", ""), Poi("2", "", ""))
    private val poiDetail = PoiDetail("1")
    lateinit var database: PoiDatabase
    lateinit var dao: PoiDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, PoiDatabase::class.java)
            .allowMainThreadQueries()
            .build();
        dao = database.poiDao().apply {
            insertPois(poiList)
            insertPoi(poiDetail)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun loadPois_shouldQueryAll() {
        dao.loadPois().test().assertValue(poiList)
    }

    @Test
    fun insertPois_shouldReplaceOnConflict() {
        val poiOne = Poi("1", "new_title", "new_geocoordinates")
        val poiTwo = Poi("2", "new_title", "new_geocoordinates")
        val expectedPoiList = listOf(poiOne, poiTwo)
        dao.insertPois(listOf(poiOne, poiTwo))
        dao.loadPois().test().assertValue(expectedPoiList)
    }

    @Test
    fun deletePois_shouldDeleteAll() {
        dao.deletePois()
        dao.loadPois().test().assertValue(emptyList())
    }

    @Test
    fun loadPoiById_shouldLoadPoiWhenExists() {
        dao.loadPoiById("1").test().assertValue(poiDetail)
        dao.loadPoiById("2").test().assertError(RuntimeException::class.java)
    }

    @Test
    fun insertPoi_shouldReplaceOnConflict() {
        val newPoiDetail = PoiDetail(id = "1", title = "new_title")
        dao.insertPoi(newPoiDetail)
        dao.loadPoiById("1").test().assertValue(newPoiDetail)
    }

    @Test
    fun deletePoi_shouldDeleteWhenExists() {
        Assertions.assertThat(dao.deletePoi("1")).isEqualTo(1)
        Assertions.assertThat(dao.deletePoi("2")).isEqualTo(0)
    }
}