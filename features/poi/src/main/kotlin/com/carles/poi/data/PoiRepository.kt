package com.carles.poi.data

import com.carles.poi.Poi
import com.carles.poi.PoiDetail
import com.carles.poi.poi.data.datasourcefactory.PoiRepo
import io.reactivex.Single

class PoiRepository(private val localDatasource: PoiLocalDatasource, private val remoteDatasource: PoiRemoteDatasource) :
    PoiRepo {

    override fun getPoiList(refresh: Boolean): Single<List<Poi>> = when (refresh) {
        true -> remoteDatasource.getPoiList().map { localDatasource.persist(it) }
        false -> localDatasource.getPoiList().onErrorResumeNext { getPoiList(true) }
    }

    override fun getPoiDetail(itemId: String, refresh: Boolean): Single<PoiDetail> = when (refresh) {
        true -> remoteDatasource.getPoiDetail(itemId).map { localDatasource.persist(it) }
        false -> localDatasource.getPoiDetail(itemId).onErrorResumeNext { getPoiDetail(itemId, true) }
    }
}