package com.carles.hyrule.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.carles.common.ui.extensions.addTo
import com.carles.common.ui.navigation.Screen
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.domain.GetMonsterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MonsterDetailUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val monster: MonsterDetail? = null,
)

@HiltViewModel
class MonsterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMonsterDetail: GetMonsterDetail
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val id = savedStateHandle.get<String>(Screen.Arguments.monsterId)?.toInt() ?: 0

    private val _uiState = MutableStateFlow(MonsterDetailUiState())
    val uiState: StateFlow<MonsterDetailUiState> = _uiState

    init {
        getMonsterDetail()
    }

    private fun getMonsterDetail() {
        getMonsterDetail.execute(id)
            .doOnSubscribe {
                _uiState.update { it.copy(loading = true) }
            }.subscribe({ monster ->
                _uiState.update { it.copy(loading = false, error = null, monster = monster) }
            }, { error ->
                Log.w("MonsterDetailViewModel", error)
                _uiState.update { it.copy(loading = false, error = error.message) }
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun retry() {
        getMonsterDetail()
    }
}