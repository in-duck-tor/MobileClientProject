package com.ithirteeng.secondpatternsclientproject.data.exchange.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: String,
    val name: String,
    val value: Double,
)

fun CurrencyEntity.toDomain() = Currency(
    id = id,
    numCode = numCode,
    charCode = charCode,
    nominal = nominal,
    name = name,
    value = value
)

fun Currency.toEntity() = CurrencyEntity(
    id = id,
    numCode = numCode,
    charCode = charCode,
    nominal = nominal,
    name = name,
    value = value
)