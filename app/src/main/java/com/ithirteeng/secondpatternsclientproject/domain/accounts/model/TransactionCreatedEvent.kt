package com.ithirteeng.secondpatternsclientproject.domain.accounts.model

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionStatus
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionTarget
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionType

data class TransactionCreatedEvent(
    val transactionId: String,
    val type: String,
    val status: String,
    val depositOn: TransactionTarget?,
    val withdrawFrom: TransactionTarget?,
)

fun TransactionCreatedEvent.toTransaction() = Transaction(
    id = transactionId,
    type = when (type) {
        "1.0" -> TransactionType.unkwnown
        "2.0" -> TransactionType.withdraw
        "3.0" -> TransactionType.deposit
        "4.0" -> TransactionType.transfer
        "5.0" -> TransactionType.transfer_to_external
        "6.0" -> TransactionType.transfer_from_external
        else -> TransactionType.unkwnown

    },
    status = when (status) {
        "2.0" -> TransactionStatus.pending
        "3.0" -> TransactionStatus.committed
        else -> TransactionStatus.canceled
    },
    startedAt = "hz",
    finishedAt = null,
    depositOn = depositOn,
    withdrawFrom = withdrawFrom,
)
