package com.carles.poi.data

import com.carles.poi.anotherPoiDetail
import com.carles.poi.anotherPoiList
import com.carles.poi.poiDetail
import com.carles.poi.poiList
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class PoiRepositoryTest {

    val localDatasource: PoiLocalDatasource = mockk(relaxed = true)
    val remoteDatasource: PoiRemoteDatasource = mockk(relaxed = true)
    val repository = PoiRepository(localDatasource, remoteDatasource)

    @Test
    fun getPoiList_refresh() {
        every { localDatasource.getPoiList() } returns Single.just(poiList)
        every { remoteDatasource.getPoiList() } returns Single.just(anotherPoiList)
        every { localDatasource.persist(anotherPoiList) } returns anotherPoiList
        repository.getPoiList(true).test().assertValue(anotherPoiList)
        verify(exactly = 0) { localDatasource.getPoiList() }
    }

    @Test
    fun getPoiList_fromLocal() {
        every { localDatasource.getPoiList() } returns Single.just(poiList)
        every { remoteDatasource.getPoiList() } returns Single.just(anotherPoiList)
        repository.getPoiList().test().assertValue(poiList)
        verify { remoteDatasource wasNot called }
    }

    @Test
    fun getPoiList_localErrorFallbackToRemote() {
        every { localDatasource.getPoiList() } returns Single.error(Throwable())
        every { remoteDatasource.getPoiList() } returns Single.just(anotherPoiList)
        every { localDatasource.persist(anotherPoiList) } returns anotherPoiList
        repository.getPoiList().test().assertValue(anotherPoiList)
    }

    @Test
    fun getPoiList_remoteError() {
        val localError = Throwable("local error")
        val remoteError = Throwable("remote error")
        every { localDatasource.getPoiList() } returns Single.error(localError)
        every { remoteDatasource.getPoiList() } returns Single.error(remoteError)
        repository.getPoiList().test().assertError(remoteError)
    }

    @Test
    fun getPoiDetail_fromLocal() {
        every { localDatasource.getPoiDetail("1") } returns Single.just(poiDetail)
        every { remoteDatasource.getPoiDetail("1") } returns Single.just(anotherPoiDetail)
        repository.getPoiDetail("1").test().assertValue(poiDetail)
        verify { remoteDatasource wasNot called }
    }

    @Test
    fun getPoiDetail_localErrorFallbackToRemote() {
        every { localDatasource.getPoiDetail("1") } returns Single.error(Throwable())
        every { remoteDatasource.getPoiDetail("1") } returns Single.just(anotherPoiDetail)
        every { localDatasource.persist(anotherPoiDetail) } returns anotherPoiDetail
        repository.getPoiDetail("1").test().assertValue(anotherPoiDetail)
    }
}