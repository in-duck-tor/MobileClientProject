package com.ithirteeng.secondpatternsclientproject.data.accounts.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE deposit_on_accountNumber = :number")
    fun observeTransactionsByDepositAccountNumber(number: String): Flow<List<TransactionEntity>>

    @Query("DELETE FROM transactions WHERE deposit_on_accountNumber = :number")
    fun deleteTransactionsByDepositAccountNumber(number: String)
}
