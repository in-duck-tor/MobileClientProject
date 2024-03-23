package com.ithirteeng.secondpatternsclientproject.data.accounts.api

import com.ithirteeng.secondpatternsclientproject.data.accounts.api.model.AccountNumber
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.CurrencyCode
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsNetworkService {

    @GET("account")
    suspend fun getAccounts(): List<Account>

    @POST("account")
    suspend fun createAccount(@Body createAccount: CreateAccount): AccountNumber

    @GET("account/{accountNumber}/transaction")
    suspend fun getAccountTransactions(@Path("accountNumber") number: String): List<Transaction>

    @POST("account/transaction")
    suspend fun makeAccountTransaction(@Body transactionRequest: TransactionRequest)

    @PUT("account/{accountNumber}/freeze")
    suspend fun freezeAccount(@Path("accountNumber") number: String): Response<Unit>

    @PUT("account/{accountNumber}/unfreeze")
    suspend fun unfreezeAccount(@Path("accountNumber") number: String): Response<Unit>

    @POST("account/{accountNumber}/close")
    suspend fun closeAccount(@Path("accountNumber") number: String): Response<Unit>

    @GET("bank/currency")
    suspend fun getCurrencyCodes(): List<CurrencyCode>

}