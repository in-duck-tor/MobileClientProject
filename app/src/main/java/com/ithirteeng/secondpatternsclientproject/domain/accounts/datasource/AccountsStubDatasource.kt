package com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest

interface AccountsStubDatasource {

    suspend fun createAccountStub(createAccount: CreateAccount, token: String): Account

    suspend fun replenishAccountStub(accountNumber: String, amount: Double, token: String)

    suspend fun withdrawFromAccountStub(accountNumber: String, amount: Double, token: String)

    suspend fun makeTransactionStub(transaction: TransactionRequest, token: String)
}