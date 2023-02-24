package com.carles.hyrule

import androidx.room.Room
import com.carles.hyrule.data.HyruleDatasourceFactoryAlt
import com.carles.hyrule.data.HyruleRepo
import com.carles.hyrule.data.HyruleRepository
import com.carles.hyrule.data.HyruleRepositoryAlt
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.local.HyruleLocalDatasource
import com.carles.hyrule.data.local.HyruleLocalDatasourceAlt
import com.carles.hyrule.data.mapper.MonsterDetailMapper
import com.carles.hyrule.data.mapper.MonstersMapper
import com.carles.hyrule.data.remote.HyruleApi
import com.carles.hyrule.data.remote.HyruleRemoteDatasource
import com.carles.hyrule.data.remote.HyruleRemoteDatasourceAlt
import com.carles.hyrule.domain.GetMonsterDetailUsecase
import com.carles.hyrule.domain.RefreshMonstersUsecase
import com.carles.hyrule.ui.MonsterDetailViewModel
import com.carles.hyrule.ui.MonstersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val hyruleModule = module {
    val REPOSITORY = named("repository")
    val REPOSITORY_ALT = named("repository-alt")

    single {
        Room.databaseBuilder(androidContext(), HyruleDatabase::class.java, "hyrule_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<HyruleDatabase>().monsterDao() }
    single { get<Retrofit>().create(HyruleApi::class.java) }

    single { HyruleLocalDatasource(dao = get(), cache = get()) }
    single { HyruleRemoteDatasource(api = get()) }
    single { MonstersMapper() }
    single { MonsterDetailMapper() }
    single<HyruleRepo>(REPOSITORY) {
        HyruleRepository(
            localDatasource = get(),
            remoteDatasource = get(),
            monstersMapper = get(),
            monsterDetailMapper = get()
        )
    }

    single { HyruleLocalDatasourceAlt(dao = get(), monstersMapper = get(), monsterDetailMapper = get(), cache = get()) }
    single {
        HyruleRemoteDatasourceAlt(
            api = get(),
            localDatasource = get(),
            monstersMapper = get(),
            monsterDetailMapper = get()
        )
    }
    single { HyruleDatasourceFactoryAlt(localDatasource = get(), remoteDatasource = get(), cache = get()) }
    single<HyruleRepo>(REPOSITORY_ALT) { HyruleRepositoryAlt(factory = get()) }

    factory { RefreshMonstersUsecase(repository = get(REPOSITORY), schedulers = get()) }
    factory { GetMonsterDetailUsecase(repository = get(REPOSITORY), schedulers = get()) }

    viewModel { MonstersViewModel(refreshMonsters = get()) }
    viewModel { (id: Int) -> MonsterDetailViewModel(id = id, getMonsterDetail = get()) }
}