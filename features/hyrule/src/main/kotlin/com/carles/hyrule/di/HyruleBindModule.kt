package com.carles.hyrule.di

import com.carles.hyrule.data.HyruleRepo
import com.carles.hyrule.data.HyruleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HyruleBindModule {

    @Binds
    @Singleton
    abstract fun bindHyruleRepository(repository: HyruleRepository): HyruleRepo
}
