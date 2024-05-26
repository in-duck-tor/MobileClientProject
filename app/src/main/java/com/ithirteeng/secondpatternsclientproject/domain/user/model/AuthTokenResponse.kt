package com.ithirteeng.secondpatternsclientproject.domain.user.model

data class AuthTokenResponse(
    val login: String,
    val roles: List<Role>,
    val token: String,
)
