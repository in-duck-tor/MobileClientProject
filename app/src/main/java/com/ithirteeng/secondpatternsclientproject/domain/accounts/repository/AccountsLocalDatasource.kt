package com.ithirteeng.secondpatternsclientproject.domain.accounts.repository

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface AccountsLocalDatasource {

    suspend fun observeAccounts(clientId: String): Flow<List<Account>>

    suspend fun fetchAccounts(accounts: List<Account>, clientId: String)

    suspend fun insertAccount(account: Account, clientId: String)

    //todo remake on fetch
    suspend fun insertTransactions(transactions: List<Transaction>)

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun observeTransactionsByAccountNumber(accountNumber: String): Flow<List<Transaction>>
}