package com.carles.hyrule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MonsterEntity::class, MonsterDetailEntity::class], version = 1)
abstract class HyruleDatabase : RoomDatabase() {

    abstract fun monsterDao(): MonsterDao
}