package com.carles.settings.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.carles.common.ui.extensions.addTo
import com.carles.settings.R
import com.carles.settings.SettingsUi
import com.carles.settings.domain.ObserveUserSettings
import com.carles.settings.domain.SetCacheExpirationTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SettingsUiState(
    val error: String? = null,
    val settings: SettingsUi? = null,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setCacheExpirationTime: SetCacheExpirationTime,
    private val observeUserSettings: ObserveUserSettings,
    private val settingsMapper: SettingsMapper
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        observeUserSettings()
    }

    private fun observeUserSettings() {
        observeUserSettings.execute().subscribe({ settings ->
            _uiState.update { it.copy(settings = settingsMapper.toUi(settings), error = null) }
        }, { error ->
            Log.w("SettingsViewModel", error)
            _uiState.update { it.copy(error = error.message) }
        }).addTo(disposables)
    }

    fun onSettingSelected(@StringRes key: Int, @StringRes selectedOption: Int) {
        when (key) {
            R.string.preferences_cache_key -> setCacheExpirationTime(settingsMapper.toCacheExpirationTimeValue(selectedOption))
        }
    }

    private fun setCacheExpirationTime(expirationTime: Int) {
        setCacheExpirationTime.execute(expirationTime).subscribe({
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