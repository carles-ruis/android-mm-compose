package com.carles.mm

import android.app.Application
import android.os.StrictMode
import com.carles.common.commonModule
import com.carles.hyrule.hyruleModule
import com.carles.settings.settingsModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, commonModule, hyruleModule, settingsModule)
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            initializeStrictMode()
        }
    }

    private fun initializeStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDropBox()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDropBox()
                .build()
        )
    }
}