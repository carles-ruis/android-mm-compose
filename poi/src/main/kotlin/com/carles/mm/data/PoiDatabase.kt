package com.carles.mm.data

import androidx.room.*
import com.carles.mm.Poi
import com.carles.mm.PoiDetail
import io.reactivex.Single

@Database(entities = arrayOf(PoiDetail::class, Poi::class), version = 1, exportSchema = false)
abstract class PoiDatabase : RoomDatabase() {

    abstract fun poiDao(): PoiDao
}

@Dao
interface PoiDao {
    @Query("SELECT * from poi")
    fun loadPois(): Single<List<Poi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPois(poiList: List<Poi>): List<Long>

    @Query("DELETE from poi")
    fun deletePois(): Int

    //  @Query("SELECT * from poi_detail where id=:id LIMIT 1")
    @Query("SELECT * from poi_detail where id=:id")
    fun loadPoiById(id: String): Single<PoiDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoi(poi: PoiDetail): Long

    @Query("DELETE from poi_detail where id=:id")
    fun deletePoi(id: String): Int
}