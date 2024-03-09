package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class Target(
    val amount: Double,
    val accountNumber: String,
    val bankCode: String,
)
