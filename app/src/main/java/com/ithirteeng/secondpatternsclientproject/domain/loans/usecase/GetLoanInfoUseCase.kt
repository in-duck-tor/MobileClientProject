package com.ithirteeng.secondpatternsclientproject.domain.loans.usecase

import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource

class GetLoanInfoUseCase(
    private val remoteDatasource: LoansRemoteDatasource,
) {

    suspend operator fun invoke(loanId: Int) =
        remoteDatasource.getLoanInfo(loanId.toString())
}
