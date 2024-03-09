package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionTarget

data class TransactionTargetEntity(
    val amount: Double,
    val accountNumber: String,
    val bankCode: String,
    val bankName: String?,
)

fun TransactionTargetEntity.toDomain(): TransactionTarget = TransactionTarget(
    amount = amount,
    accountNumber = accountNumber,
    bankCode = bankCode,
    bankName = bankName
)

fun TransactionTarget.toEntity(): TransactionTargetEntity = TransactionTargetEntity(
    amount = amount,
    accountNumber = accountNumber,
    bankCode = bankCode,
    bankName = bankName
)
