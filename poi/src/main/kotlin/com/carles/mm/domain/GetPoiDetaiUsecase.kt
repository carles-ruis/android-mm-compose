package com.carles.mm.domain

import com.carles.core.domain.AppSchedulers
import com.carles.mm.PoiDetail
import com.carles.mm.poi.data.datasourcefactory.PoiRepo
import io.reactivex.Single

class GetPoiDetaiUsecase(private val repository: PoiRepo, private val schedulers: AppSchedulers) {

    operator fun invoke(itemId: String): Single<PoiDetail> =
        repository.getPoiDetail(itemId, false).subscribeOn(schedulers.io).observeOn(schedulers.ui)
}