package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction

import androidx.room.TypeConverter
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun toDb(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun fromDb(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}