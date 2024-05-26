package com.ithirteeng.secondpatternsclientproject.domain.loans.usecase

import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource

class GetUserLoansUseCase(
    private val remoteDatasource: LoansRemoteDatasource,
) {

    suspend operator fun invoke(clientId: String) =
        remoteDatasource.getUserLoans(clientId)
}
