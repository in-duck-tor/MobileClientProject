package com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan

data class ApplicationInfo(
    val clientId: Long,
    val loanProgramId: Long,
    val borrowedAmount: Double,
    val loanTerm: Long,
    val clientAccountNumber: String?,
)