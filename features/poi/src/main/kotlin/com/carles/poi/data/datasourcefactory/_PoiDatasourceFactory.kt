package com.carles.poi.data.datasourcefactory

import com.carles.core.data.Cache
import com.carles.core.data.CacheKey
import com.carles.poi.poi.data.datasourcefactory._PoiDatasource
import com.carles.poi.poi.data.datasourcefactory._PoiLocalDatasource
import com.carles.poi.poi.data.datasourcefactory._PoiRemoteDatasource

@SuppressWarnings("ClassNaming")
class _PoiDatasourceFactory(
    private val localDatasource: _PoiLocalDatasource,
    private val remoteDatasource: _PoiRemoteDatasource,
    private val cache: Cache
) {

    fun retrieveDatasource(refresh: Boolean, key: CacheKey): _PoiDatasource = when {
        refresh -> remoteDatasource
        cache.isCached(key) -> localDatasource
        else -> remoteDatasource
    }

    fun retrieveLocalDatasource() = localDatasource

    fun retrieveRemoteDatasource() = remoteDatasource
}