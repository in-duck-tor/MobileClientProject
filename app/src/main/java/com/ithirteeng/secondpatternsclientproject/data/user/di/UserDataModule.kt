package com.ithirteeng.secondpatternsclientproject.data.user.di

import com.ithirteeng.secondpatternsclientproject.data.user.repository.UserRepositoryImpl
import com.ithirteeng.secondpatternsclientproject.data.user.storage.TokenStorage
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository
import org.koin.dsl.module

val userDataModule = module {
    single { TokenStorage(context = get()) }

    single<UserRepository> {
        UserRepositoryImpl(tokenStorage = get())
    }
}
