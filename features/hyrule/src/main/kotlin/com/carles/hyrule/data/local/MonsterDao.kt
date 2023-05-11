package com.carles.hyrule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MonsterDao {

    @Query("SELECT * FROM monster ORDER BY name ASC")
    suspend fun loadMonsters(): List<MonsterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonsters(monsters: List<MonsterEntity>): List<Long>

    @Query("DELETE FROM monster")
    suspend fun deleteMonsters(): Int

    @Query("SELECT * FROM monster_detail WHERE _id=:id")
    suspend fun loadMonsterDetail(id: Int): MonsterDetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonsterDetail(monster: MonsterDetailEntity): Long
}