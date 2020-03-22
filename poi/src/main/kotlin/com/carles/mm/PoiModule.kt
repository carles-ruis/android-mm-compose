package com.carles.mm

import androidx.room.Room
import com.carles.mm.data.*
import com.carles.mm.domain.FetchPoiListUsecase
import com.carles.mm.domain.GetPoiDetaiUsecase
import com.carles.mm.poi.data.datasourcefactory.*
import com.carles.mm.ui.viewmodel.PoiDetailViewModel
import com.carles.mm.ui.viewmodel.PoiListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
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
    single(POI_REPO) { PoiRepository(localDatasource = get(), remoteDatasource = get()) as PoiRepo }

    single { _PoiLocalDatasource(dao = get(), cache = get()) }
    single { _PoiRemoteDatasource(api = get()) }
    single { _PoiDatasourceFactory(localDatasource = get(), remoteDatasource = get(), cache = get()) }
    single(_POI_REPO) { _PoiRepository(datasourceFactory = get()) as PoiRepo }

    factory { FetchPoiListUsecase(repository = get(POI_REPO), schedulers = get()) }
    factory { GetPoiDetaiUsecase(repository = get(POI_REPO), schedulers = get()) }

    viewModel { PoiListViewModel(fetchPoiListUsecase = get()) }
    viewModel { (id: String) -> PoiDetailViewModel(id = id, getPoiDetailUsecase = get()) }
}