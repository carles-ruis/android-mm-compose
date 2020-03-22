package com.carles.mm.domain

import com.carles.core.domain.AppSchedulers
import com.carles.mm.Poi
import com.carles.mm.poi.data.datasourcefactory.PoiRepo
import io.reactivex.Single

class FetchPoiListUsecase(private val repository: PoiRepo, private val schedulers: AppSchedulers) {

    operator fun invoke(): Single<List<Poi>> = repository.getPoiList(true).subscribeOn(schedulers.io).observeOn(schedulers.ui)
}