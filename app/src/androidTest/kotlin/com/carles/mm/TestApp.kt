package com.carles.mm

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.runner.AndroidJUnitRunner
import com.carles.common.commonModule
import com.carles.common.domain.AppSchedulers
import com.carles.mm.api.PoiTestApi
import com.carles.poi.data.PoiApi
import com.carles.poi.data.PoiDatabase
import com.carles.poi.poiModule
import com.carles.settings.settingsModule
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.common.context.startKoin
import org.koin.dsl.module

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(appModule, commonModule, poiModule, settingsModule, testModule)
        }
    }
}

class TestAppRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}

private val testModule = module {
    single(override = true) {
        AppSchedulers(
            io = AndroidSchedulers.mainThread(),
            ui = AndroidSchedulers.mainThread(),
            new = AndroidSchedulers.mainThread()
        )
    }

    single(override = true) {
        Room.inMemoryDatabaseBuilder(androidContext(), PoiDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single(override = true) { PoiTestApi() as PoiApi }
}
