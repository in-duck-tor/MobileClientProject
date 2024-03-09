package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account

data class CreateAccount(
    val currencyCode: String = "RUS",
    val customComment: String?,
)
