package com.ithirteeng.secondpatternsclientproject.domain.exchange.model

data class Currency(
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: String,
    val name: String,
    val value: Double,
)
