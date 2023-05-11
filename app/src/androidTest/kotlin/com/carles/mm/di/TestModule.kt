package com.carles.mm.di

import android.content.Context
import androidx.room.Room
import com.carles.common.di.CommonModule
import com.carles.common.domain.AppDispatchers
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.local.MonsterDao
import com.carles.hyrule.data.remote.HyruleApi
import com.carles.hyrule.di.HyruleProvideModule
import com.carles.mm.fakes.FakeHyruleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonModule::class, HyruleProvideModule::class]
)
@Module
object TestModule {

    @Provides
    @Singleton
    fun provideDispatchers(): AppDispatchers {
        val dispatcher = Dispatchers.Main
        return AppDispatchers(dispatcher, dispatcher, dispatcher)
    }

    @Provides
    @Singleton
    fun provideHyruleDatabase(@ApplicationContext context: Context): HyruleDatabase {
        return Room.inMemoryDatabaseBuilder(context, HyruleDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMonsterDao(database: HyruleDatabase): MonsterDao {
        return database.monsterDao()
    }

    @Provides
    @Singleton
    fun provideHyruleApi(): HyruleApi {
        return FakeHyruleApi()
    }
}