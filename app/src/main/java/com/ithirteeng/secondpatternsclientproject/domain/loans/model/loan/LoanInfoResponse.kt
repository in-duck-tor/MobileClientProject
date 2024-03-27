package com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.PaymentType

data class LoanInfoResponse(
    val id: Int,
    val borrowedAmount: Double,
    val interestRate: String?,
    val clientAccountNumber: String?,
    val approvalDate: String,
    val borrowingDate: String?,
    val plannedPaymentsNumber: Int,
    val paymentType: PaymentType,
    val loanBody: Double,
    val loanDebt: Double,
    val penalty: Double,
)

