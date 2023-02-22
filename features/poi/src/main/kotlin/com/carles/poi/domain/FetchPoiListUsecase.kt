package com.carles.poi.domain

import com.carles.common.domain.AppSchedulers
import com.carles.poi.Poi
import com.carles.poi.data.PoiRepo
import io.reactivex.Single

class FetchPoiListUsecase(private val repository: PoiRepo, private val schedulers: AppSchedulers) {

    operator fun invoke(): Single<List<Poi>> = repository.getPoiList(true).subscribeOn(schedulers.io).observeOn(schedulers.ui)
}