package com.ithirteeng.secondpatternsclientproject.data.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.toDomain
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.toEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.toDomain
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.toEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.AccountsDao
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.TransactionsDao
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountsLocalDatasourceImpl(
    private val accountsDao: AccountsDao,
    private val transactionsDao: TransactionsDao,
) : AccountsLocalDatasource {

    override suspend fun observeAccounts(login: String, filter: String): Flow<List<Account>> {
        return accountsDao.observeAccountsByClientId(login, filter).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun fetchAccounts(accounts: List<Account>, login: String) {
        accountsDao.fetchAccounts(accounts.map { it.toEntity(login) }, login)
    }

    override suspend fun insertAccount(account: Account, login: String) {
        accountsDao.insertAccount(account.toEntity(login))
    }

    override suspend fun getAccount(number: String): Account {
        return accountsDao.getAccount(number).toDomain()
    }

    override suspend fun insertTransactions(
        transactions: List<Transaction>,
        accountNumber: String
    ) {
        transactionsDao.insertTransactions(transactions.map { it.toEntity(accountNumber) })
    }

    override suspend fun insertTransaction(transaction: Transaction, accountNumber: String) {
        transactionsDao.insertTransaction(transaction.toEntity(accountNumber))
    }

    override suspend fun observeTransactionsByAccountNumber(accountNumber: String): Flow<List<Transaction>> {
        return transactionsDao.observeTransactionsByAccountNumber(accountNumber)
            .map { list ->
                list.map { it.toDomain() }
            }
    }
}