package com.ithirteeng.secondpatternsclientproject.data.accounts.api

import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsV2NetworkService {

    @POST("account")
    suspend fun createAccount(@Body createAccount: CreateAccount): Response<Unit>
}
