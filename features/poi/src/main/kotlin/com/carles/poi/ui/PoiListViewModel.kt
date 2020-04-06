package com.carles.poi.ui

import androidx.lifecycle.ViewModel
import com.carles.core.ui.viewmodel.*
import com.carles.poi.Poi
import com.carles.poi.domain.FetchPoiListUsecase
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

    private fun onGetPoiSuccess(data: List<Poi>) {
        _observablePoiList.setSuccess(data)
    }

    private fun onGetPoiError(throwable: Throwable) {
        _observablePoiList.setError(throwable.message)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun retry() {
        fetchPoiList()
    }
}
