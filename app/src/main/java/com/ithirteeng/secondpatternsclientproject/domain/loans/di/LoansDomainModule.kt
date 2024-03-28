package com.ithirteeng.secondpatternsclientproject.domain.loans.di

import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanInfoUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanProgramsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetUserLoansUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.MakePaymentToLoanUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.SubmitLoanApplicationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val loansDomainModule = module {

    factoryOf(::GetUserLoansUseCase)
    factoryOf(::GetLoanInfoUseCase)
    factoryOf(::GetLoanProgramsUseCase)
    factoryOf(::MakePaymentToLoanUseCase)
    factoryOf(::SubmitLoanApplicationUseCase)
}