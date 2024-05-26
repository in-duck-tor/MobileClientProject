package com.ithirteeng.secondpatternsclientproject.domain.loans.model.base

data class Loan(
    val id: String,
    val clientId: String,
    val amount: Double,
    val createdAt: String,
    val dueTo: String,
)
