package com.ithirteeng.secondpatternsclientproject.app

import android.app.Application
import com.ithirteeng.secondpatternsclientproject.app.di.appModule
import com.ithirteeng.secondpatternsclientproject.common.network.di.networkModule
import com.ithirteeng.secondpatternsclientproject.common.storage.di.databaseModule
import com.ithirteeng.secondpatternsclientproject.data.accounts.di.accountsDataModule
import com.ithirteeng.secondpatternsclientproject.data.exchange.di.exchangeDataModule
import com.ithirteeng.secondpatternsclientproject.data.loans.di.loansDataModule
import com.ithirteeng.secondpatternsclientproject.data.theme.di.themeDataModule
import com.ithirteeng.secondpatternsclientproject.data.user.di.userDataModule
import com.ithirteeng.secondpatternsclientproject.domain.accounts.di.accountsDomainModule
import com.ithirteeng.secondpatternsclientproject.domain.exchange.di.exchangeDomainModule
import com.ithirteeng.secondpatternsclientproject.domain.loans.di.loansDomainModule
import com.ithirteeng.secondpatternsclientproject.domain.theme.di.themeDomainModule
import com.ithirteeng.secondpatternsclientproject.domain.user.di.userDomainModule
import com.ithirteeng.secondpatternsclientproject.features.client.main.di.mainClientModule
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.di.accountInfoModule
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.di.createAccountModule
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di.myAccountsMainModule
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.di.transactionModule
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.di.createLoanModule
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.di.loanInfoModule
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.di.myLoansMainModule
import com.ithirteeng.secondpatternsclientproject.features.common.settings.di.settingsModule
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
                appModule,

                networkModule,
                databaseModule,

                userDataModule,
                userDomainModule,

                accountsDataModule,
                accountsDomainModule,

                themeDataModule,
                themeDomainModule,

                loansDataModule,
                loansDomainModule,

                exchangeDataModule,
                exchangeDomainModule,

                splashModule,
                loginModule,
                registrationModule,
                mainClientModule,
                myAccountsMainModule,
                accountInfoModule,
                createAccountModule,
                transactionModule,

                myLoansMainModule,
                createLoanModule,
                loanInfoModule,

                settingsModule,
            )
        }
    }
}