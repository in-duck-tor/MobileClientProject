package com.ithirteeng.secondpatternsclientproject.data.loans.datasource

import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkServiceV1
import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkServiceV2
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanApplicationResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

class LoansRemoteDatasourceImpl(
    private val networkServiceV2: LoansNetworkServiceV2,
    private val networkServiceV1: LoansNetworkServiceV1,
) : LoansRemoteDatasource {

    override suspend fun makePaymentToLoan(loanId: String, payoffBody: EarlyPayoffBody) {
        val response = networkServiceV2.addPaymentToLoan(loanId, payoffBody)
        if (!response.isSuccessful) throw Exception(response.message())
    }

    override suspend fun getLoanInfo(loanId: String): LoanInfoResponse {
        return networkServiceV2.getLoanInfo(loanId)
    }

    override suspend fun getUserLoans(clientId: String): List<LoanInfoShort> {
        return networkServiceV2.getUserLoans()
    }

    override suspend fun submitLoanApplication(applicationInfo: ApplicationInfo): LoanApplicationResponse {
        return networkServiceV2.submitLoanApplication(applicationInfo)
    }

    override suspend fun getLoanPrograms(): List<LoanProgramResponse> {
        return networkServiceV1.getLoanPrograms()
    }

    override suspend fun getCreditScore(): Int {
        return networkServiceV1.getUserCreditScore().creditScore
    }
}
