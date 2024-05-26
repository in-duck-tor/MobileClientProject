package com.ithirteeng.secondpatternsclientproject.domain.loans.datasource

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan
import kotlinx.coroutines.flow.Flow

interface LoansStubDatasource {

    suspend fun observeLoans(clientId: String): Flow<List<Loan>>

    suspend fun deleteLoan(loanId: String)

    suspend fun insertLoan(loan: Loan)

    suspend fun changeAmount(payment: Double, loanId: String)

    suspend fun getLoan(loanId: String): Loan
}