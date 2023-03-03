package com.carles.hyrule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MonsterDao {

    @Query("SELECT * FROM monster ORDER BY name ASC")
    fun loadMonsters(): Single<List<MonsterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonsters(monsters: List<MonsterEntity>): Single<List<Long>>

    @Query("DELETE FROM monster")
    fun deleteMonsters(): Single<Int>

    @Query("SELECT * FROM monster_detail WHERE _id=:id")
    fun loadMonsterDetail(id: Int): Single<MonsterDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonsterDetail(monster: MonsterDetailEntity): Single<Long>
}