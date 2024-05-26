package com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramInfoResponse

data class LoanApplicationResponse(
    val id: Long,
    val clientId: Long,
    val loanProgram: LoanProgramInfoResponse,
    val borrowedAmount: Double,
    val loanTerm: Long,
    val applicationState: String,
    val loan: LoanInfoResponse,
)