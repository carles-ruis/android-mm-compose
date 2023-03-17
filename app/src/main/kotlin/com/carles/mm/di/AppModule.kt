package com.carles.mm.di

import com.carles.common.ui.navigation.Navigate
import com.carles.mm.ui.NavigateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideNavigate(navigate: NavigateImpl): Navigate
}