package com.carles.settings

import com.carles.settings.data.SettingsRepository
import com.carles.settings.domain.ResetCacheExpirationUsecase
import com.carles.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    single { SettingsRepository(cache = get()) }
    factory { ResetCacheExpirationUsecase(repository = get(), schedulers = get()) }
    viewModel { SettingsViewModel(resetCacheExpirationUsecase = get()) }
}