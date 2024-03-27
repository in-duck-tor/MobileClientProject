package com.ithirteeng.secondpatternsclientproject.domain.loans.model.program

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.PaymentScheduleType
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.PaymentType

data class LoanProgramInfoResponse(
    val interestRate: Double,
    val paymentType: PaymentType,
    val paymentScheduleType: PaymentScheduleType,
    val periodInterval: Int,
)
