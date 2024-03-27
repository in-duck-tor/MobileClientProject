package com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan

data class LoanInfoShort(
    val id: Int,
    val borrowedAmount: Double,
    val interestRate: String?,
    val plannedPaymentsNumber: Int,
    val loanBody: Double,
    val loanDebt: Double,
    val penalty: Double,
)
