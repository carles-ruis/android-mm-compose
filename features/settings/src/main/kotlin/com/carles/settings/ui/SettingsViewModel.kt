package com.carles.settings.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.carles.common.ui.addTo
import com.carles.settings.domain.ResetCacheExpiration
import io.reactivex.disposables.CompositeDisposable

class SettingsViewModel(private val resetCacheExpiration: ResetCacheExpiration) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun onPreferenceCacheChanged() {
        resetCacheExpiration.execute().subscribe({
            // nothing to do here
        }, { error ->
            Log.w("SettingsViewModel", error)
        }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}