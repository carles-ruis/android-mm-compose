package com.carles.hyrule.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.carles.common.ui.extensions.addTo
import com.carles.hyrule.Monster
import com.carles.hyrule.domain.RefreshMonsters
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed class MonstersUiState {
    object Loading : MonstersUiState()
    class Error(val message: String) : MonstersUiState()
    class Data(val monsters: List<Monster>) : MonstersUiState()
}

@HiltViewModel
class MonstersViewModel @Inject constructor(
    private val refreshMonsters: RefreshMonsters
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private var _uiState = MutableStateFlow<MonstersUiState>(MonstersUiState.Loading)
    val uiState: StateFlow<MonstersUiState> = _uiState

    init {
        refreshMonsters()
    }

    private fun refreshMonsters() {
        refreshMonsters.execute()
            .doOnSubscribe {
                _uiState.value = MonstersUiState.Loading
            }.subscribe({ monsters ->
                _uiState.value = MonstersUiState.Data(monsters)
            }, { error ->
                Log.w("MonstersViewModel", error)
                _uiState.value = MonstersUiState.Error(error.message ?: error.toString())
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun retry() {
        refreshMonsters()
    }
}