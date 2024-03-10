package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class Transaction(
    val id: String,
    val type: TransactionType,
    val status: TransactionStatus,
    val startedAt: String,
    val finishedAt: String?,
    val depositOn: TransactionTarget?,
    val withdrawFrom: TransactionTarget?,
)