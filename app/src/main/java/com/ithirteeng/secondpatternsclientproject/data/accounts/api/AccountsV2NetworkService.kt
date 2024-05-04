package com.ithirteeng.secondpatternsclientproject.data.accounts.api

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsV2NetworkService {

    @POST("account")
    suspend fun createAccount(@Body createAccount: CreateAccount): Response<Unit>

    @PUT("account/{accountNumber}/freeze")
    suspend fun freezeAccount(@Path("accountNumber") number: String): Response<Unit>

    @PUT("account/{accountNumber}/unfreeze")
    suspend fun unfreezeAccount(@Path("accountNumber") number: String): Response<Unit>

    @POST("account/{accountNumber}/close")
    suspend fun closeAccount(@Path("accountNumber") number: String): Response<Unit>
}
