package com.carles.settings

import com.carles.settings.data.SettingsLocalDatasource
import com.carles.settings.data.SettingsRepository
import com.carles.settings.domain.UpdateCacheExpirationUsecase
import com.carles.settings.ui.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    single { SettingsLocalDatasource(cache = get()) }
    single { SettingsRepository(datasource = get()) }
    factory { UpdateCacheExpirationUsecase(repository = get(), schedulers = get()) }
    viewModel { SettingsViewModel(updateCacheExpirationUsecase = get()) }

}