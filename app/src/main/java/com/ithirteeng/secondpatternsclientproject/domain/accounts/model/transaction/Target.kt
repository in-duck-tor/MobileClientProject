package com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction

data class Target(
    val accountNumber: String,
    val bankCode: String = "000000000",
)
