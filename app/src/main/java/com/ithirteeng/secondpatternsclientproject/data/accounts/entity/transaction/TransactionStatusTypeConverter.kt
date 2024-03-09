package com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction

import androidx.room.TypeConverter
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionStatus

class TransactionStatusTypeConverter {

    @TypeConverter
    fun toDb(value: TransactionStatus): String {
        return value.name
    }

    @TypeConverter
    fun fromDb(value: String): TransactionStatus {
        return TransactionStatus.valueOf(value)
    }
}