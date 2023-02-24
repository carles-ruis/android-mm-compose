package com.carles.hyrule.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.carles.common.ui.MutableResourceLiveData
import com.carles.common.ui.ResourceLiveData
import com.carles.common.ui.addTo
import com.carles.common.ui.setError
import com.carles.common.ui.setLoading
import com.carles.common.ui.setSuccess
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.domain.GetMonsterDetailUsecase
import io.reactivex.disposables.CompositeDisposable

class MonsterDetailViewModel(
    private val id: Int,
    private val getMonsterDetail: GetMonsterDetailUsecase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _monsterDetail = MutableResourceLiveData<MonsterDetail>()
    val monsterDetail: ResourceLiveData<MonsterDetail> = _monsterDetail

    init {
        getMonsterDetail()
    }

    private fun getMonsterDetail() {
        getMonsterDetail.execute(id)
            .doOnSubscribe {
                _monsterDetail.setLoading()
            }.subscribe({ monster ->
                _monsterDetail.setSuccess(monster)
            }, { error ->
                Log.w("MonsterDetailViewModel", error)
                _monsterDetail.setError(error.message)
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