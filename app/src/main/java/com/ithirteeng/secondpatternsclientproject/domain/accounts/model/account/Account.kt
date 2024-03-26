package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account

data class Account(
    val number: String,
    val currencyCode: String,
    val amount: Double,
    val state: AccountState,
    val customComment: String?,
)
