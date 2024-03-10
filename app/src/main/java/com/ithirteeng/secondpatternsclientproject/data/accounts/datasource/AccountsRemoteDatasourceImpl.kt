package com.ithirteeng.secondpatternsclientproject.data.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest

class AccountsRemoteDatasourceImpl(
    private val service: AccountsNetworkService,
) : AccountsRemoteDatasource {

    override suspend fun createAccount(data: CreateAccount): Account {
        val number = service.createAccount(data)
        val accounts = service.getAccounts()
        return accounts.find { it.number == number.accountNumber }!!
    }

    override suspend fun getAccountsList(): List<Account> {
        return service.getAccounts()
    }

    override suspend fun freezeAccount(accountNumber: String) {
        val response = service.freezeAccount(accountNumber)
        if (response.isSuccessful) {
            return
        } else {
            throw Exception(response.code().toString())
        }
    }

    override suspend fun closeAccount(accountNumber: String) {
        val response = service.closeAccount(accountNumber)
        if (response.isSuccessful) {
            return
        } else {
            throw Exception(response.code().toString())
        }
    }

    override suspend fun unfreezeAccount(accountNumber: String) {
        val response = service.unfreezeAccount(accountNumber)
        if (response.isSuccessful) {
            return
        } else {
            throw Exception(response.code().toString())
        }
    }

    override suspend fun makeTransaction(transaction: TransactionRequest) {
        service.makeAccountTransaction(transaction)
    }

    override suspend fun getAccountTransactions(accountNumber: String): List<Transaction> {
        return service.getAccountTransactions(accountNumber)
    }


}