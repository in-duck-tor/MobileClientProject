package com.ithirteeng.secondpatternsclientproject.common.network.di

import com.ithirteeng.secondpatternsclientproject.common.network.interceptor.AuthInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.network.provideAuthorizedOkHttpClient
import com.ithirteeng.secondpatternsclientproject.common.network.provideLoggingInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.provideNetworkConnectionInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.provideOkHttpClient
import com.ithirteeng.secondpatternsclientproject.common.network.provideRetrofit
import com.ithirteeng.secondpatternsclientproject.common.network.provideXmlRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    factory { provideLoggingInterceptor() }
    factory { provideNetworkConnectionInterceptor(context = get()) }

    factory { AuthInterceptor(getLocalTokenUseCase = get()) }

    single(named(ConnectionType.UNAUTHORIZED.name)) {
        provideOkHttpClient(
            loggingInterceptor = get(),
            networkConnectionInterceptor = get()
        )
    }

    single(named(ConnectionType.UNAUTHORIZED.name)) {
        provideRetrofit(
            okHttpClient = get(named(ConnectionType.UNAUTHORIZED.name)),
            url = "http://89.19.214.8/api/v1/"
        )
    }

    single(named(ConnectionType.AUTHORIZED.name)) {
        provideAuthorizedOkHttpClient(
            loggingInterceptor = get(),
            networkConnectionInterceptor = get(),
            authInterceptor = get()
        )
    }

    single(named(ConnectionType.AUTHORIZED.name)) {
        provideRetrofit(
            okHttpClient = get(named(ConnectionType.AUTHORIZED.name)),
            url = "http://89.19.214.8/api/v1/"
        )
    }

    single(named(ConnectionType.UNAUTHORIZED_XML.name)) {
        provideXmlRetrofit(
            okHttpClient = get(named(ConnectionType.UNAUTHORIZED.name)),
            url = "https://www.cbr.ru/scripts/"
        )
    }

}