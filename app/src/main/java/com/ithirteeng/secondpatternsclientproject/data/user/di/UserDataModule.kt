package com.ithirteeng.secondpatternsclientproject.data.user.di

import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.data.user.repository.UserLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.user.repository.UserRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.user.service.UserInfoService
import com.ithirteeng.secondpatternsclientproject.data.user.service.UserNetworkService
import com.ithirteeng.secondpatternsclientproject.data.user.storage.TokenStorage
import com.ithirteeng.secondpatternsclientproject.data.user.stub.StubUserDatasource
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val userDataModule = module {
    single { TokenStorage(context = get()) }

    single {
        createRetrofitService<UserNetworkService>(
            retrofit = get(named(ConnectionType.AUTHORIZED.name))
        )
    }

    single {
        createRetrofitService<UserInfoService>(
            retrofit = get(named(ConnectionType.AUTHORIZED_USER.name))
        )
    }

    single<UserLocalDatasource> {
        UserLocalDatasourceImpl(tokenStorage = get())
    }

    singleOf(::UserRemoteDatasourceImpl) bind UserRemoteDatasource::class
}
