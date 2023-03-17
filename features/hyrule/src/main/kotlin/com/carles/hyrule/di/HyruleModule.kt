package com.carles.hyrule.di

import android.content.Context
import androidx.room.Room
import com.carles.hyrule.data.HyruleRepo
import com.carles.hyrule.data.HyruleRepository
import com.carles.hyrule.data.HyruleRepositoryAlt
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.local.MonsterDao
import com.carles.hyrule.data.remote.HyruleApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HyruleBindModule {

    @HyruleRepositoryQualifier
    @Binds
    @Singleton
    abstract fun bindHyruleRepository(repository: HyruleRepository): HyruleRepo

    @HyruleRepositoryAltQualifier
    @Binds
    @Singleton
    abstract fun bindHyruleRepositoryAlt(repository: HyruleRepositoryAlt): HyruleRepo
}

@Module
@InstallIn(SingletonComponent::class)
object HyruleProvideModule {

    @Provides
    @Singleton
    fun provideHyruleDatabase(
        @ApplicationContext
        context: Context
    ): HyruleDatabase {
        return Room.databaseBuilder(context, HyruleDatabase::class.java, "hyrule_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMonsterDao(database: HyruleDatabase): MonsterDao {
        return database.monsterDao()
    }

    @Provides
    @Singleton
    fun provideHyruleApi(retrofit: Retrofit): HyruleApi {
        return retrofit.create(HyruleApi::class.java)
    }
}
