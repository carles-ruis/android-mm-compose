package com.carles.mm

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.runner.AndroidJUnitRunner
import com.carles.core.coreModule
import com.carles.core.domain.AppSchedulers
import com.carles.mm.api.PoiTestApi
import com.carles.mm.data.PoiApi
import com.carles.mm.data.PoiDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(coreModule, poiModule, testModule)
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
