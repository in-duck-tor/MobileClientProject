package com.ithirteeng.secondpatternsclientproject.domain.loans.usecase

import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody

class MakePaymentToLoanUseCase(
    private val remoteDatasource: LoansRemoteDatasource,
) {

    suspend operator fun invoke(loanId: String, payoffBody: EarlyPayoffBody) =
        remoteDatasource.makePaymentToLoan(loanId, payoffBody)
}
