package com.ithirteeng.secondpatternsclientproject.domain.loans.model

data class ApplicationInfo(
    val clientId: Int,
    val loanProgramId: Int,
    val borrowedAmount: Double,
    val loanTerm: Int,
    val clientAccountNumber: String,
)