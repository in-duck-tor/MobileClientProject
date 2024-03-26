package com.ithirteeng.secondpatternsclientproject.domain.user.model

data class UserAuthData(
    val login: String,
    val email: String? = null,
    val password: String,
)
