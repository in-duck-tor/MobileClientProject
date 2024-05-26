package com.ithirteeng.secondpatternsclientproject.domain.loans.usecase

import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource

class GetCreditScoreUseCase(
    private val remoteDatasource: LoansRemoteDatasource,
) {

    suspend operator fun invoke() = remoteDatasource.getCreditScore()
}