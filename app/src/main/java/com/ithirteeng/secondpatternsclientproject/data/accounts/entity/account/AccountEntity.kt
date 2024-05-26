package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState

@Entity(tableName = "accounts")
@TypeConverters(AccountStateTypeConverter::class)
data class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    val number: String,
    val clientId: String,
    val currencyCode: String?,
    val amount: Double,
    val state: AccountState,
    val customComment: String?,
    val isHidden: Boolean = false,
)

fun AccountEntity.toDomain(): Account = Account(
    number = number,
    currencyCode = currencyCode ?: "RUB",
    amount = amount,
    state = state,
    customComment = customComment,
    isHidden = isHidden,
)

fun Account.toEntity(clientId: String): AccountEntity = AccountEntity(
    number = number,
    currencyCode = currencyCode,
    amount = amount,
    state = state,
    customComment = customComment,
    clientId = clientId,
    isHidden = isHidden,
)