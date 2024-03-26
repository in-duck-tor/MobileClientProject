package com.ithirteeng.secondpatternsclientproject.common.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.AccountEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.TransactionEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.AccountsDao
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.TransactionsDao
import com.ithirteeng.secondpatternsclientproject.data.exchange.entity.CurrencyEntity
import com.ithirteeng.secondpatternsclientproject.data.exchange.storage.CurrencyDao
import com.ithirteeng.secondpatternsclientproject.data.loans.entity.LoanEntity
import com.ithirteeng.secondpatternsclientproject.data.loans.storage.LoanDao

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class,
        LoanEntity::class,
        CurrencyEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class BankDatabase : RoomDatabase() {

    abstract fun accountsDao(): AccountsDao

    abstract fun transactionsDao(): TransactionsDao

    abstract fun loanDao(): LoanDao

    abstract fun currencyDao(): CurrencyDao
}