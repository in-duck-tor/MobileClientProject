package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class TransactionRequest(
    val amount: Double,
    val depositOn: Target?,
    val withdrawFrom: Target?,
)
