package com.ithirteeng.secondpatternsclientproject.app

import android.app.Application
import com.ithirteeng.secondpatternsclientproject.features.client.main.di.mainClientModule
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di.myAccountsMainModule
import com.ithirteeng.secondpatternsclientproject.features.common.login.di.loginModule
import com.ithirteeng.secondpatternsclientproject.features.common.registration.di.registrationModule
import com.ithirteeng.secondpatternsclientproject.features.common.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(
                splashModule,
                registrationModule,
                loginModule,
                mainClientModule,
                myAccountsMainModule,
            )
        }
    }
}