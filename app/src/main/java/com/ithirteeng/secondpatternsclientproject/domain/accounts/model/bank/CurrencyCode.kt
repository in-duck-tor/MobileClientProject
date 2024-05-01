package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank


data class CurrencyCode(
    val code: String,
    val numericCode: Int,
    val exchangeRate: Double,
)
