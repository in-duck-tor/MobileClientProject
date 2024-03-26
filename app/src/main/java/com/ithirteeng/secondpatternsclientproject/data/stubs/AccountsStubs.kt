package com.ithirteeng.secondpatternsclientproject.data.stubs

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState

object AccountsStubs {

    fun createAccountsList(): List<Account> {
        val list = mutableListOf<Account>()
        for (i in 0..14) {
            val state = if (i % 3 == 0) {
                AccountState.active
            } else if (i % 3 == 1) {
                AccountState.closed
            } else {
                AccountState.frozen
            }
            list.add(createAccount(i, state))
        }
        return list.toList()
    }

    private fun createAccount(
        id: Int,
        state: AccountState,
    ) = Account(
        number = "id$id",
        currencyCode = "RUS",
        amount = 12345230.0,
        state = state,
        customComment = "Say something $id!"

    )
}
