package com.ithirteeng.secondpatternsclientproject.data.loans.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey
    val id: String,
    val clientId: String,
    val amount: Double,
    val createdAt: String,
    val dueTo: String,
)

fun LoanEntity.toDomain() = Loan(
    id, clientId, amount, createdAt, dueTo
)

fun Loan.toEntity() = LoanEntity(
    id, clientId, amount, createdAt, dueTo
)
