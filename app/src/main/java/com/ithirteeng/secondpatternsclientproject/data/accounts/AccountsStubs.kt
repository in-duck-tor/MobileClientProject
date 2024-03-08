package com.ithirteeng.secondpatternsclientproject.data.accounts

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountInfo

object AccountsStubs {

    fun createAccountsList(): List<AccountInfo> = listOf(
        createShortAccount(id = 1, isActive = false),
        createShortAccount(id = 2, isActive = true),
        createShortAccount(id = 3, isActive = true),
        createShortAccount(id = 4, isActive = true),
        createShortAccount(id = 5, isActive = false),
        createShortAccount(id = 6, isActive = true),
        createShortAccount(id = 7, isActive = false),
        createShortAccount(id = 8, isActive = false),
        createShortAccount(id = 9, isActive = true),
        createShortAccount(id = 10, isActive = true),
    )

    private fun createShortAccount(
        id: Int,
        isActive: Boolean = true,
    ) = AccountInfo(
        name = "name $id",
        number = "$id",
        isActive = isActive,
        balance = 23423542
    )
}
