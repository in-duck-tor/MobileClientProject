package com.ithirteeng.secondpatternsclientproject.data.exchange.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ithirteeng.secondpatternsclientproject.data.exchange.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency")
    suspend fun getCurrencyRates(): List<CurrencyEntity>

    @Transaction
    suspend fun insertCurrencyRates(rates: List<CurrencyEntity>) {
        deleteAllRates()
        insertRates(rates)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<CurrencyEntity>)

    @Query("DELETE FROM currency")
    suspend fun deleteAllRates()
}