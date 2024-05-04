package com.ithirteeng.secondpatternsclientproject.common.network

import android.content.Context
import com.ithirteeng.secondpatternsclientproject.common.network.interceptor.AuthInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.interceptor.IdempotencyInterceptor
import com.ithirteeng.secondpatternsclientproject.common.network.interceptor.NetworkConnectionInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return loggingInterceptor
}

fun provideNetworkConnectionInterceptor(context: Context): NetworkConnectionInterceptor =
    NetworkConnectionInterceptor(context)


fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideXmlRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    networkConnectionInterceptor: NetworkConnectionInterceptor,
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

fun provideAuthorizedOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    networkConnectionInterceptor: NetworkConnectionInterceptor,
    authInterceptor: AuthInterceptor,
    idempotencyInterceptor: IdempotencyInterceptor,
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(idempotencyInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()