package com.carles.mm.di

import com.carles.common.Navigate
import com.carles.mm.NavigateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    @ActivityScoped
    abstract fun bindNavigate(navigate: NavigateImpl): Navigate
}
