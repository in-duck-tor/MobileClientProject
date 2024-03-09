package com.ithirteeng.secondpatternsclientproject.data.accounts.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Transaction
    fun fetchAccounts(accounts: List<AccountEntity>, clientId: String) {
        deleteAccountsByClientId(clientId = clientId)
        insertAccounts(accounts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccounts(accounts: List<AccountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE clientId = :clientId")
    fun observeAccountsByClientId(clientId: String): Flow<List<AccountEntity>>

    @Query("DElETE FROM accounts WHERE clientId = :clientId")
    fun deleteAccountsByClientId(clientId: String)
}
