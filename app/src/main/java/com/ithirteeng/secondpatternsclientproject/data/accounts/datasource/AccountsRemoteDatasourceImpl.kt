package com.ithirteeng.secondpatternsclientproject.data.accounts.datasource

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.ithirteeng.secondpatternsclientproject.common.mapper.toFirebaseLogin
import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsV2NetworkService
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.Bank
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.CurrencyCode
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import kotlinx.coroutines.tasks.await

class AccountsRemoteDatasourceImpl(
    private val service: AccountsNetworkService,
    private val serviceV2: AccountsV2NetworkService,
    private val firebaseDatabase: DatabaseReference,
) : AccountsRemoteDatasource {

    override suspend fun createAccountV2(data: CreateAccount) {
        val response = serviceV2.createAccount(data)
        if (response.isSuccessful) {
            return
        } else {
            throw Exception(response.message())
        }
    }

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


    override suspend fun getCurrencyCodes(): List<CurrencyCode> {
        return service.getCurrencyCodes()
    }


    override suspend fun addHiddenAccount(login: String, accountNumber: String) {
        firebaseDatabase.child(HIDDEN_ACCOUNTS).child(login.toFirebaseLogin()).child(accountNumber)
            .setValue(true)
    }

    override suspend fun getHiddenAccountNumbers(login: String): List<String>? {
        val accounts =
            firebaseDatabase.child(HIDDEN_ACCOUNTS).child(login.toFirebaseLogin()).get().await()
        return try {
            (accounts.value as HashMap<String, Boolean>).toHiddenAccountsList()
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    private fun HashMap<String, Boolean>.toHiddenAccountsList(): List<String> {
        return this.keys.toList()
    }

    override suspend fun makeAccountVisible(login: String, accountNumber: String) {
        firebaseDatabase.child(HIDDEN_ACCOUNTS).child(login.toFirebaseLogin()).child(accountNumber)
            .removeValue()
    }

    override suspend fun getBanksInfo(): List<Bank> {
        return service.getBankInfo()
    }

    private companion object {

        private const val TAG = "AccountsRemoteDatasource"
        private const val HIDDEN_ACCOUNTS = "hidden_accounts"
    }
}