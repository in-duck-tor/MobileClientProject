package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class NewTransactionRequest(
    val newTransaction: TransactionRequest,
    val executeImmediate: Boolean = true,
    val requestedTransactionTtl: TransactionTtl = TransactionTtl(),
)
