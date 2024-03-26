package com.ithirteeng.secondpatternsclientproject.data.loans.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ithirteeng.secondpatternsclientproject.data.loans.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {

    @Query("SELECT * FROM loans WHERE clientId = :clientId")
    fun observeLoans(clientId: String): Flow<List<LoanEntity>>

    @Query("DELETE FROM loans WHERE id = :loanId")
    fun deleteLoan(loanId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoan(loanEntity: LoanEntity)

    @Query("SELECT * FROM loans WHERE id = :loanId")
    fun getLoan(loanId: String): LoanEntity
}
