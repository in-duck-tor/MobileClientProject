package com.ithirteeng.secondpatternsclientproject.data.user.service

import com.ithirteeng.secondpatternsclientproject.domain.user.model.AuthTokenResponse
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import retrofit2.http.Body
import retrofit2.http.POST

interface UserNetworkService {

    @POST("login")
    suspend fun login(@Body body: UserAuthData): AuthTokenResponse
}