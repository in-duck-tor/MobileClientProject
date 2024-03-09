package com.ithirteeng.secondpatternsclientproject.common.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.AccountEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.TransactionEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.AccountsDao
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.TransactionsDao

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class BankDatabase : RoomDatabase() {

    abstract fun accountsDao(): AccountsDao

    abstract fun transactionsDao(): TransactionsDao
}