package com.carles.hyrule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MonsterDao {

    @Query("SELECT * FROM monster")
    fun loadMonsters(): Single<List<MonsterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonsters(monsters: List<MonsterEntity>): List<Long>

    @Query("DELETE FROM monster")
    fun deleteMonsters(): Int

    @Query("SELECT * FROM monster_detail WHERE _id=:id")
    fun loadMonsterDetail(id: Int): Single<MonsterDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonsterDetail(monster: MonsterDetailEntity): Long
}