package com.carles.poi

import androidx.room.Room
import com.carles.poi.data.PoiApi
import com.carles.poi.data.PoiDatabase
import com.carles.poi.data.PoiLocalDatasource
import com.carles.poi.data.PoiRemoteDatasource
import com.carles.poi.data.PoiRepo
import com.carles.poi.data.PoiRepository
import com.carles.poi.data.datasourcefactory._PoiDatasourceFactory
import com.carles.poi.data.datasourcefactory._PoiLocalDatasource
import com.carles.poi.data.datasourcefactory._PoiRemoteDatasource
import com.carles.poi.data.datasourcefactory._PoiRepository
import com.carles.poi.domain.FetchPoiListUsecase
import com.carles.poi.domain.GetPoiDetaiUsecase
import com.carles.poi.ui.PoiDetailViewModel
import com.carles.poi.ui.PoiListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@SuppressWarnings("VariableNaming")
val poiModule = module {
    val POI_REPO = named("poi_repo")
    val _POI_REPO = named("_poi_repo")

    single {
        Room.databaseBuilder(androidContext(), PoiDatabase::class.java, "poi_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { (get() as PoiDatabase).poiDao() }
    single { (get() as Retrofit).create(PoiApi::class.java) }
    single { PoiLocalDatasource(dao = get(), cache = get()) }
    single { PoiRemoteDatasource(api = get()) }
    single<PoiRepo>(POI_REPO) { PoiRepository(localDatasource = get(), remoteDatasource = get()) }

    single { _PoiLocalDatasource(dao = get(), cache = get()) }
    single { _PoiRemoteDatasource(api = get()) }
    single { _PoiDatasourceFactory(localDatasource = get(), remoteDatasource = get(), cache = get()) }
    single<PoiRepo>(_POI_REPO) { _PoiRepository(datasourceFactory = get()) }

    factory { FetchPoiListUsecase(repository = get(POI_REPO), schedulers = get()) }
    factory { GetPoiDetaiUsecase(repository = get(POI_REPO), schedulers = get()) }

    viewModel { PoiListViewModel(fetchPoiListUsecase = get()) }
    viewModel { (id: String) -> PoiDetailViewModel(id = id, getPoiDetailUsecase = get()) }
}