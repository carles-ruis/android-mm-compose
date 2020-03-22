package com.carles.mm.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.carles.core.ui.viewmodel.*
import com.carles.mm.Poi
import com.carles.mm.domain.FetchPoiListUsecase
import io.reactivex.disposables.CompositeDisposable

class PoiListViewModel(private val fetchPoiListUsecase: FetchPoiListUsecase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val _observablePoiList = MutableResourceLiveData<List<Poi>>()
    val observablePoiList: ResourceLiveData<List<Poi>> = _observablePoiList

    init {
        fetchPoiList()
    }

    private fun fetchPoiList() {
        fetchPoiListUsecase()
            .doOnSubscribe { _observablePoiList.setLoading() }
            .subscribe(::onGetPoiSuccess, ::onGetPoiError)
            .addTo(disposables)
    }

    @VisibleForTesting
    fun onGetPoiSuccess(data: List<Poi>) {
        _observablePoiList.setSuccess(data)
    }

    @VisibleForTesting
    fun onGetPoiError(throwable: Throwable) {
        _observablePoiList.setError(throwable.message)
    }

    fun retry() {
        fetchPoiList()
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
