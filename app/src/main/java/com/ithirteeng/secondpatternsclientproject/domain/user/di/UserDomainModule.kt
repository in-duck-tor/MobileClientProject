package com.ithirteeng.secondpatternsclientproject.domain.user.di

import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.WipeUserDataUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val userDomainModule = module {

    factoryOf(::GetLocalTokenUseCase)
    factoryOf(::SaveTokenLocallyUseCase)
    factoryOf(::SaveUserLoginUseCase)
    factoryOf(::GetUserLoginUseCase)
    factoryOf(::WipeUserDataUseCase)
}
