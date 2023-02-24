package com.carles.common

import com.carles.common.data.AppPreferences
import com.carles.common.data.Cache
import com.carles.common.domain.AppSchedulers
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://botw-compendium.herokuapp.com"

val commonModule = module {

    single { AppPreferences(context = androidApplication()) }
    single { Cache(preferences = get()) }
    single {
        AppSchedulers(
            Schedulers.io(),
            AndroidSchedulers.mainThread(),
            Schedulers.newThread()
        )
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ChuckerInterceptor.Builder(androidApplication()).build())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }
}
