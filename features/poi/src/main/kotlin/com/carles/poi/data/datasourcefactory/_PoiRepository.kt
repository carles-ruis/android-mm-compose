@file:Suppress("Filename", "ClassNaming")
package com.carles.poi.data.datasourcefactory

import com.carles.core.data.CacheItems
import com.carles.core.data.CacheKey
import com.carles.poi.Poi
import com.carles.poi.PoiDetail
import com.carles.poi.data.PoiRepo
import io.reactivex.Single

class _PoiRepository(private val datasourceFactory: _PoiDatasourceFactory) : PoiRepo {

    override fun getPoiList(refresh: Boolean): Single<List<Poi>> =
        datasourceFactory
            .retrieveDatasource(refresh, CacheKey(CacheItems.POI_LIST))
            .getPoiList()
            //     .onErrorResumeNext(datasourceFactory.retrieveRemoteDatasource().getPoiList())
            .flatMap { datasourceFactory.retrieveLocalDatasource().persist(it) }

    override fun getPoiDetail(itemId: String, refresh: Boolean): Single<PoiDetail> =
        datasourceFactory
            .retrieveDatasource(refresh, CacheKey(CacheItems.POI_DETAIL, itemId))
            .getPoiDetail(itemId)
//            .onErrorResumeNext(datasourceFactory.retrieveLocalDatasource().getPoiDetail(itemId))
            .flatMap { datasourceFactory.retrieveLocalDatasource().persist(it) }
}