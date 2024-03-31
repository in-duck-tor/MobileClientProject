package com.ithirteeng.secondpatternsclientproject.data.loans.api

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanApplicationResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoansNetworkServiceV2 {

    @POST("loan/{loanId}/pay/regularly")
    suspend fun addPaymentToLoan(@Path("loanId") loanId: String, @Body payoffBody: EarlyPayoffBody): Response<Unit>

    @GET("loan/{loanId}")
    suspend fun getLoanInfo(@Path("loanId") loanId: String): LoanInfoResponse

    @GET("loan/loans")
    suspend fun getUserLoans(): List<LoanInfoShort>

    @POST("application")
    suspend fun submitLoanApplication(@Body applicationInfo: ApplicationInfo): LoanApplicationResponse

    @GET("program")
    suspend fun getLoanPrograms(): List<LoanProgramResponse>

}
