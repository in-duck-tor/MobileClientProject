package com.ithirteeng.secondpatternsclientproject.domain.user.di

import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val userDomainModule = module {

    factoryOf(::GetLocalTokenUseCase)
    factoryOf(::SaveTokenLocallyUseCase)
}
