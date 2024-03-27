package com.ithirteeng.secondpatternsclientproject.data.loans.datasource

import com.ithirteeng.secondpatternsclientproject.data.loans.entity.toDomain
import com.ithirteeng.secondpatternsclientproject.data.loans.entity.toEntity
import com.ithirteeng.secondpatternsclientproject.data.loans.storage.LoanDao
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoansStubDatasourceImpl(
    private val loansDao: LoanDao,
) : LoansStubDatasource {

    override suspend fun observeLoans(clientId: String): Flow<List<Loan>> {
        return loansDao.observeLoans(clientId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteLoan(loanId: String) {
        loansDao.deleteLoan(loanId)
    }

    override suspend fun insertLoan(loan: Loan) {
        loansDao.insertLoan(loan.toEntity())
    }

    override suspend fun changeAmount(payment: Double, loanId: String) {
        val loan = loansDao.getLoan(loanId)
        val updatedLoan = loan.copy(
            amount = loan.amount - payment
        )
        if (updatedLoan.amount < 0.0) {
            loansDao.deleteLoan(loanId)
        } else {
            loansDao.insertLoan(updatedLoan)
        }
    }

    override suspend fun getLoan(loanId: String): Loan {
        return loansDao.getLoan(loanId).toDomain()
    }
}