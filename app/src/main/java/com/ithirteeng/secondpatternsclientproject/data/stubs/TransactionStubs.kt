package com.ithirteeng.secondpatternsclientproject.data.stubs

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionStatus
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionTarget
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionType
import java.time.LocalDateTime

object TransactionStubs {

    fun createTransactions(accountNumber: String): List<Transaction> {
        val list = mutableListOf<Transaction>()

        for (i in 0..14) {
            val type = when (i % 5) {
                0 -> TransactionType.deposit
                1 -> TransactionType.transfer
                2 -> TransactionType.withdraw
                3 -> TransactionType.transfer_from_external
                else -> TransactionType.transfer_to_external
            }
            list.add(createTransaction(accountNumber, i, type))
        }
        return list.toList()
    }

    private fun createTransaction(
        accountNumber: String,
        id: Int,
        type: TransactionType,
    ): Transaction {
        val accountTargetNumber = if (id > 0) id - 1 else id + 1
        return Transaction(
            id = "tr$id",
            type = type,
            status = TransactionStatus.committed,
            startedAt = LocalDateTime.now().toString(),
            finishedAt = null,
            depositOn = createTarget(accountNumber),
            withdrawFrom = createTarget("id$accountTargetNumber")

        )
    }

    private fun createTarget(accountNumber: String) = TransactionTarget(
        amount = 1000.0,
        accountNumber = accountNumber,
        bankCode = "234234324",
        bankName = "BANKBLIN"
    )
}