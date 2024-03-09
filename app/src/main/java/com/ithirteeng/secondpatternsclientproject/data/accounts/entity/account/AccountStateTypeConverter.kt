package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account

import androidx.room.TypeConverter
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState

class AccountStateTypeConverter {

    @TypeConverter
    fun toDb(value: AccountState): String {
        return value.name
    }

    @TypeConverter
    fun fromDb(value: String): AccountState {
        return AccountState.valueOf(value)
    }
}
