package com.ithirteeng.secondpatternsclientproject.data.accounts.datasource

import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.account.toEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.TransactionEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.entity.transaction.TransactionTargetEntity
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.AccountsDao
import com.ithirteeng.secondpatternsclientproject.data.accounts.storage.TransactionsDao
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionStatus
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionType
import java.time.LocalDateTime
import kotlin.random.Random

class AccountsStubDatasourceImpl(
    private val accountsDao: AccountsDao,
    private val transactionsDao: TransactionsDao,
) : AccountsStubDatasource {

    override suspend fun createAccountStub(createAccount: CreateAccount, token: String): Account {
        val account = Account(
            number = Random.nextInt().toString(),
            currencyCode = "RUB",
            amount = 1000.0,
            state = AccountState.active,
            customComment = createAccount.customComment
        )
        accountsDao.insertAccount(account.toEntity(token))
        return account
    }

    override suspend fun replenishAccountStub(
        accountNumber: String,
        amount: Double,
        token: String,
    ) {
        val account = accountsDao.getAccount(accountNumber)
        val updatedAccount = account.copy(
            amount = account.amount + amount
        )
        transactionsDao.insertTransaction(
            createSingleAccountTransaction(
                amount, accountNumber, TransactionType.deposit
            )
        )
        accountsDao.insertAccount(updatedAccount)
    }

    override suspend fun withdrawFromAccountStub(
        accountNumber: String,
        amount: Double,
        token: String,
    ) {
        val account = accountsDao.getAccount(accountNumber)
        if (account.amount - amount < 0.0) {
            throw Exception("AMOUNT mustn't be bigger than account")
        }
        val updatedAccount = account.copy(
            amount = account.amount - amount
        )
        transactionsDao.insertTransaction(
            createSingleAccountTransaction(
                amount, accountNumber, TransactionType.withdraw
            )
        )
        accountsDao.insertAccount(updatedAccount)
    }

    override suspend fun makeTransactionStub(transaction: TransactionRequest, token: String) {
        val depositAccount = accountsDao.getAccount(transaction.depositOn!!.accountNumber)
        accountsDao.insertAccount(
            depositAccount.copy(
                amount = depositAccount.amount + transaction.amount
            )
        )

        val withdrawAccount = accountsDao.getAccount(transaction.withdrawFrom!!.accountNumber)
        accountsDao.insertAccount(
            withdrawAccount.copy(
                amount = withdrawAccount.amount - transaction.amount
            )
        )

        transactionsDao.insertTransaction(createTransactionFromRequest(transaction))
    }

    private fun createTransactionFromRequest(transaction: TransactionRequest): TransactionEntity {
        return TransactionEntity(
            id = Random.nextInt().toString(),
            type = TransactionType.transfer,
            status = TransactionStatus.committed,
            startedAt = LocalDateTime.now().toString(),
            finishedAt = LocalDateTime.now().toString(),
            depositOn = TransactionTargetEntity(
                amount = transaction.amount,
                accountNumber = transaction.depositOn!!.accountNumber,
                currencyCode = "RUB",
                bankCode = "00000000",
                bankName = null
            ),
            accountNumber = transaction.depositOn.accountNumber,
            withdrawFrom = TransactionTargetEntity(
                amount = transaction.amount,
                accountNumber = transaction.depositOn.accountNumber,
                currencyCode = "RUB",
                bankCode = "00000000",
                bankName = null,
            )
        )
    }

    private fun createSingleAccountTransaction(
        amount: Double,
        accountNumber: String,
        transactionType: TransactionType,
    ): TransactionEntity {
        return TransactionEntity(
            id = Random.nextInt().toString(),
            type = transactionType,
            status = TransactionStatus.committed,
            startedAt = LocalDateTime.now().toString(),
            finishedAt = LocalDateTime.now().toString(),
            depositOn = TransactionTargetEntity(
                amount = amount,
                accountNumber = accountNumber,
                currencyCode = "RUB",
                bankCode = "00000000",
                bankName = null
            ),
            accountNumber = accountNumber,
            withdrawFrom = TransactionTargetEntity(
                amount = amount,
                accountNumber = accountNumber,
                currencyCode = "RUB",
                bankCode = "00000000",
                bankName = null
            )
        )
    }
}