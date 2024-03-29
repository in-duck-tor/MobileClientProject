package com.ithirteeng.secondpatternsclientproject.domain.loans.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo

class SubmitLoanApplicationUseCase(
    private val remoteDatasource: LoansRemoteDatasource,
) {

    suspend operator fun invoke(applicationInfo: ApplicationInfo) = provideResult {
        remoteDatasource.submitLoanApplication(applicationInfo)
    }
}
