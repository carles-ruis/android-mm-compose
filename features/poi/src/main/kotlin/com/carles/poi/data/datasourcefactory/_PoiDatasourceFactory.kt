package com.carles.poi.data.datasourcefactory

import com.carles.common.data.Cache
import com.carles.common.data.CacheKey

@Suppress("ClassNaming", "Filename")
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