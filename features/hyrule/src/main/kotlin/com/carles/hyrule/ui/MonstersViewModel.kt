package com.carles.hyrule.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carles.hyrule.Monster
import com.carles.hyrule.domain.RefreshMonsters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MonstersUiState {
    object Loading : MonstersUiState()
    data class Error(val message: String) : MonstersUiState()
    data class Data(val monsters: List<Monster>) : MonstersUiState()
}

@HiltViewModel
class MonstersViewModel @Inject constructor(
    private val refreshMonsters: RefreshMonsters
) : ViewModel() {

    private var _uiState = MutableStateFlow<MonstersUiState>(MonstersUiState.Loading)
    val uiState: StateFlow<MonstersUiState> = _uiState

    init {
        refreshMonsters()
    }

    private fun refreshMonsters() {
        viewModelScope.launch {
            _uiState.value = MonstersUiState.Loading

            try {
                val monsters = refreshMonsters.execute()
                _uiState.value = MonstersUiState.Data(monsters)
            } catch (e: Exception) {
                Log.w("MonstersViewModel", e)
                _uiState.value = MonstersUiState.Error(e.message ?: e.toString())
            }
        }
    }

    fun retry() {
        refreshMonsters()
    }
}