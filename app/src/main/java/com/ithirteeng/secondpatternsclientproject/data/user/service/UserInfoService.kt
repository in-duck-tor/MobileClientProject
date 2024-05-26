package com.ithirteeng.secondpatternsclientproject.data.user.service

import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAccount
import retrofit2.http.GET

interface UserInfoService {

    @GET("userinfo")
    suspend fun getUserData(): UserAccount
}