package com.carles.mm

import android.app.Application
import com.carles.common.commonModule
import com.carles.poi.poiModule
import com.carles.settings.settingsModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, commonModule, poiModule, settingsModule)
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}