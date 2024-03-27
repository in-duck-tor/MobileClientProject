package com.ithirteeng.secondpatternsclientproject.domain.loans.model

data class LoanProgramInfo(
    val interestRate: Double,
    val paymentType: PaymentType,
    val paymentScheduleType: PaymentScheduleType,
    val periodInterval: Int,
)
