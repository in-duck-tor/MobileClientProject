package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionStatus
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionType

@Entity(tableName = "transactions")
@TypeConverters(TransactionTypeConverter::class)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val type: TransactionType,
    val status: TransactionStatus,
    val startedAt: String,
    val finishedAt: String?,
    @Embedded(prefix = "deposit_on_")
    val depositOn: TransactionTargetEntity?,
    @Embedded(prefix = "withdraw_from_")
    val withdrawFrom: TransactionTargetEntity?,
)

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    type = type,
    status = status,
    startedAt = startedAt,
    finishedAt = finishedAt,
    depositOn = depositOn?.toDomain(),
    withdrawFrom = withdrawFrom?.toDomain()
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type,
    status = status,
    startedAt = startedAt,
    finishedAt = finishedAt,
    depositOn = depositOn?.toEntity(),
    withdrawFrom = withdrawFrom?.toEntity()
)
