package com.ithirteeng.secondpatternsclientproject.domain.accounts.model

data class TransactionUpdatedEvent(
    val transactionId: String,
    val type: String,
    val status: String,
)
