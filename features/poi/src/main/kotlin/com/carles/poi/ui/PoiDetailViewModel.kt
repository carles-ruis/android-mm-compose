package com.carles.poi.ui

import androidx.lifecycle.ViewModel
import com.carles.core.ui.MutableResourceLiveData
import com.carles.core.ui.ResourceLiveData
import com.carles.core.ui.addTo
import com.carles.core.ui.setError
import com.carles.core.ui.setLoading
import com.carles.core.ui.setSuccess
import com.carles.poi.PoiDetail
import com.carles.poi.domain.GetPoiDetaiUsecase
import io.reactivex.disposables.CompositeDisposable

class PoiDetailViewModel(private val id: String, private val getPoiDetailUsecase: GetPoiDetaiUsecase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val _observablePoiDetail = MutableResourceLiveData<PoiDetail>()
    val observablePoiDetail: ResourceLiveData<PoiDetail> = _observablePoiDetail

    init {
        getPoiDetail()
    }

    private fun getPoiDetail() {
        getPoiDetailUsecase(id)
            .doOnSubscribe { _observablePoiDetail.setLoading() }
            .subscribe(::onGetPoiDetailSuccess, ::onGetPoiDetailError)
            .addTo(disposables)
    }

    private fun onGetPoiDetailSuccess(data: PoiDetail) {
        _observablePoiDetail.setSuccess(data)
    }

    private fun onGetPoiDetailError(throwable: Throwable) {
        _observablePoiDetail.setError(throwable.message)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun retry() {
        getPoiDetail()
    }
}
