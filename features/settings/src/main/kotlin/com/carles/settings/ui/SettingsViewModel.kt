package com.carles.settings.ui

import androidx.lifecycle.ViewModel
import com.carles.core.ui.viewmodel.addTo
import com.carles.settings.domain.UpdateCacheExpirationUsecase
import io.reactivex.disposables.CompositeDisposable

class SettingsViewModel(private val updateCacheExpirationUsecase: UpdateCacheExpirationUsecase) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun updateCacheExpiration() {
        updateCacheExpirationUsecase().subscribe().addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}