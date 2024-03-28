package com.ithirteeng.secondpatternsclientproject.data.loans.datasource

import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkService
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanApplicationResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

class LoansRemoteDatasourceImpl(
    private val networkService: LoansNetworkService,
) : LoansRemoteDatasource {

    override suspend fun makePaymentToLoan(loanId: String, payoffBody: EarlyPayoffBody) {
        networkService.addPaymentToLoan(loanId, payoffBody)
    }

    override suspend fun getLoanInfo(loanId: String): LoanInfoResponse {
        return networkService.getLoanInfo(loanId)
    }

    override suspend fun getUserLoans(clientId: String): List<LoanInfoShort> {
        return networkService.getUserLoans(clientId)
    }

    override suspend fun submitLoanApplication(applicationInfo: ApplicationInfo): LoanApplicationResponse {
        return networkService.submitLoanApplication(applicationInfo)
    }

    override suspend fun getLoanPrograms(): List<LoanProgramResponse> {
        return networkService.getLoanPrograms()
    }
}
