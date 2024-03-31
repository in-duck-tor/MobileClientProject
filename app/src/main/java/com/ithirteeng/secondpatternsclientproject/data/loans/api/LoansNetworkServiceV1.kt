package com.ithirteeng.secondpatternsclientproject.data.loans.api

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse
import retrofit2.http.GET

interface LoansNetworkServiceV1 {

    @GET("program")
    suspend fun getLoanPrograms(): List<LoanProgramResponse>

}
