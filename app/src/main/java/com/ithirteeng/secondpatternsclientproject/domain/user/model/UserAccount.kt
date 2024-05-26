package com.ithirteeng.secondpatternsclientproject.domain.user.model

import com.google.gson.annotations.SerializedName

data class UserAccount(
    @SerializedName("account_type")
    val accountType: String,
    val login: String,
)
