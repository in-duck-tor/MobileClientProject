package com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface AccountsLocalDatasource {

    suspend fun observeAccounts(login: String, filter: String): Flow<List<Account>>

    suspend fun fetchAccounts(accounts: List<Account>, login: String)

    suspend fun insertAccount(account: Account, login: String)

    suspend fun getAccount(number: String): Account

    //todo remake on fetch
    suspend fun insertTransactions(transactions: List<Transaction>, accountNumber: String)

    suspend fun insertTransaction(transaction: Transaction, accountNumber: String)

    suspend fun observeTransactionsByAccountNumber(accountNumber: String): Flow<List<Transaction>>
}