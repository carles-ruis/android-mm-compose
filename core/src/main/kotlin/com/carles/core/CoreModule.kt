package com.carles.core

import com.carles.core.data.Cache
import com.carles.core.domain.AppSchedulers
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://t21services.herokuapp.com"

val coreModule = module {
    single { Cache() }
    single {
        AppSchedulers(
            Schedulers.io(),
            AndroidSchedulers.mainThread(),
            Schedulers.newThread()
        )
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
            .baseUrl(BASE_URL)
            .build()
    }
}
