package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.Loan
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateLoanViewModel(
    getLocalTokenUseCase: GetLocalTokenUseCase,
    private val loansStubDatasource: LoansStubDatasource,
) : ViewModel() {

    private val token = getLocalTokenUseCase()

    val isMade = MutableStateFlow(false)

    fun createLoan(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            loansStubDatasource.insertLoan(
                Loan(
                    id = Random.nextInt().toString(),
                    clientId = token,
                    amount = amount,
                    createdAt = LocalDateTime.now().toString(),
                    dueTo = LocalDateTime.now().toString()
                )
            )
            isMade.value = true
        }
    }
}