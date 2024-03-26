package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.Loan
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateLoanViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val loansStubDatasource: LoansStubDatasource,
) : ViewModel() {

    private val login = getUserLoginUseCase()

    val isMade = MutableStateFlow(false)

    fun createLoan(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            loansStubDatasource.insertLoan(
                Loan(
                    id = Random.nextInt().toString(),
                    clientId = login,
                    amount = amount,
                    createdAt = LocalDateTime.now().toString(),
                    dueTo = LocalDateTime.now().toString()
                )
            )
            isMade.value = true
        }
    }
}