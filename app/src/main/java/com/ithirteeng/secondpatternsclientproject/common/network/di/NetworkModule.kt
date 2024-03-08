package com.ithirteeng.secondpatternsclientproject.common.network.di

import com.ithirteeng.secondpatternsclientproject.common.network.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.network.provideLoggingInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.provideNetworkConnectionInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.provideOkHttpClient
import com.ithirteeng.secondpatternsclientproject.common.network.provideRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    factory { provideLoggingInterceptor() }
    factory { provideNetworkConnectionInterceptor(context = get()) }

    single(named(ConnectionType.UNAUTHORIZED.name)) {
        provideOkHttpClient(
            loggingInterceptor = get(),
            networkConnectionInterceptor = get()
        )
    }

    single(named(ConnectionType.UNAUTHORIZED.name)) {
        provideRetrofit(
            okHttpClient = get(named(ConnectionType.UNAUTHORIZED.name)),
            url = "https://theaxolotlapi.netlify.app"
        )
    }
}