package com.carles.settings.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.carles.common.ui.addTo
import com.carles.settings.domain.UpdateCacheExpirationUsecase
import io.reactivex.disposables.CompositeDisposable

class SettingsViewModel(private val updateCacheExpirationUsecase: UpdateCacheExpirationUsecase) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun updateCacheExpiration() {
        updateCacheExpirationUsecase.execute().subscribe({}, { error ->
            Log.w("SettingsViewModel", error)
        }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}