package com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramInfoResponse

data class LoanApplicationResponse(
    val id: Int,
    val clientId: Int,
    val loanProgram: LoanProgramInfoResponse,
    val borrowedAmount: Double,
    val loanTerm: Long,
    val applicationState: String,
    val loan: LoanInfoResponse,
)