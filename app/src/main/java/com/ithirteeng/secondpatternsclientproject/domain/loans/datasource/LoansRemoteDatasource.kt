package com.ithirteeng.secondpatternsclientproject.domain.loans.datasource

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanApplicationResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

interface LoansRemoteDatasource {

    suspend fun makePaymentToLoan(loanId: String, payoffBody: EarlyPayoffBody)

    suspend fun getLoanInfo(loanId: String): LoanInfoResponse

    suspend fun getUserLoans(clientId: String): List<LoanInfoShort>

    suspend fun submitLoanApplication(applicationInfo: ApplicationInfo): LoanApplicationResponse

    suspend fun getLoanPrograms(): List<LoanProgramResponse>

    suspend fun getCreditScore(): Int
}
