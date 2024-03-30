package com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.Bank
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.CurrencyCode
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest

interface AccountsRemoteDatasource {

    suspend fun createAccount(data: CreateAccount): Account

    suspend fun getAccountsList(): List<Account>

    suspend fun freezeAccount(accountNumber: String)

    suspend fun closeAccount(accountNumber: String)

    suspend fun unfreezeAccount(accountNumber: String)


    suspend fun makeTransaction(transaction: TransactionRequest)

    suspend fun getAccountTransactions(accountNumber: String): List<Transaction>

    suspend fun getCurrencyCodes(): List<CurrencyCode>


    suspend fun addHiddenAccount(login: String, accountNumber: String)

    suspend fun getHiddenAccountNumbers(login: String): List<String>?

    suspend fun makeAccountVisible(login: String, accountNumber: String)

    suspend fun getBanksInfo(): List<Bank>
}
