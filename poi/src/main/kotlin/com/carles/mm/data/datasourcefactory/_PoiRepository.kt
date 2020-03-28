package com.carles.mm.poi.data.datasourcefactory

import com.carles.core.data.CacheItems
import com.carles.core.data.CacheKey
import com.carles.mm.Poi
import com.carles.mm.PoiDetail
import com.carles.mm.data.datasourcefactory._PoiDatasourceFactory
import io.reactivex.Single

interface PoiRepo {

    fun getPoiList(refresh: Boolean = false): Single<List<Poi>>

    fun getPoiDetail(itemId: String, refresh: Boolean = false): Single<PoiDetail>
}

@SuppressWarnings("ClassNaming")
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