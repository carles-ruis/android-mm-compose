package com.carles.mm

import android.app.Application
import androidx.room.Room
import com.carles.common.commonModule
import com.carles.common.domain.AppSchedulers
import com.carles.hyrule.data.local.HyruleDatabase
import com.carles.hyrule.data.remote.HyruleRemoteDatasource
import com.carles.hyrule.hyruleModule
import com.carles.mm.api.FakeHyruleApi
import com.carles.settings.settingsModule
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(appModule, commonModule, hyruleModule, settingsModule, testModule)
        }
    }
}

private val testModule = module {
    single {
        AppSchedulers(
            io = AndroidSchedulers.mainThread(),
            ui = AndroidSchedulers.mainThread(),
            new = AndroidSchedulers.mainThread()
        )
    }

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), HyruleDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { FakeHyruleApi() }
    single { HyruleRemoteDatasource(api = get<FakeHyruleApi>()) }
}
