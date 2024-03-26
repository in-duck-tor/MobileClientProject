package com.ithirteeng.secondpatternsclientproject.domain.user.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
