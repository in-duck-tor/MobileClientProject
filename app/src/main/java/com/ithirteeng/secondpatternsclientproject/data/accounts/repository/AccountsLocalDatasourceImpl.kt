package com.ithirteeng.secondpatternsclientproject.data.accounts.repository

import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.toDomain
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.toEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.toDomain
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.toEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.AccountsDao
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.TransactionsDao
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountsLocalDatasourceImpl(
    private val accountsDao: AccountsDao,
    private val transactionsDao: TransactionsDao,
) : AccountsLocalDatasource {

    override suspend fun observeAccounts(clientId: String, filter: String): Flow<List<Account>> {
        return accountsDao.observeAccountsByClientId(clientId, filter).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun fetchAccounts(accounts: List<Account>, clientId: String) {
        accountsDao.fetchAccounts(accounts.map { it.toEntity(clientId) }, clientId)
    }

    override suspend fun insertAccount(account: Account, clientId: String) {
        accountsDao.insertAccount(account.toEntity(clientId))
    }

    override suspend fun getAccount(number: String): Account {
        return accountsDao.getAccount(number).toDomain()
    }

    override suspend fun insertTransactions(transactions: List<Transaction>) {
        transactionsDao.insertTransactions(transactions.map { it.toEntity() })
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionsDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun observeTransactionsByAccountNumber(accountNumber: String): Flow<List<Transaction>> {
        return transactionsDao.observeTransactionsByDepositAccountNumber(accountNumber)
            .map { list ->
                list.map { it.toDomain() }
            }
    }
}