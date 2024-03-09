package com.ithirteeng.secondpatternsclientproject.data.accounts.repository

import com.ithirteeng.secondpatternsclientproject.common.network.NoConnectivityException
import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.data.stubs.AccountsStubs
import com.ithirteeng.secondpatternsclientproject.data.stubs.TransactionStubs
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource

class AccountsRemoteDatasourceImpl(
    private val service: AccountsNetworkService,
) : AccountsRemoteDatasource {

    override suspend fun createAccount(data: CreateAccount): Account {
        throw NoConnectivityException()
    }

    override suspend fun getAccountsList(): List<Account> {
        return AccountsStubs.createAccountsList()
    }

    override suspend fun freezeAccount() {
        throw NoConnectivityException()
    }

    override suspend fun closeAccount() {
        throw NoConnectivityException()
    }

    override suspend fun unfreezeAccount() {
        throw NoConnectivityException()
    }

    override suspend fun makeTransaction(transaction: TransactionRequest) {
        throw NoConnectivityException()
    }

    override suspend fun getAccountTransactions(accountNumber: String): List<Transaction> {
        return TransactionStubs.createTransactions(accountNumber)
    }

}