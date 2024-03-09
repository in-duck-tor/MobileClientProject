package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class TransactionTarget(
    val amount: Double,
    val accountNumber: String,
    val bankCode: String,
    val bankName: String?,
)
