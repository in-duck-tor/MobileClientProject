package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class TransactionRequest(
    val type: TransactionType,
    val depositOn: Target,
    val withdrawFrom: Target,
)
