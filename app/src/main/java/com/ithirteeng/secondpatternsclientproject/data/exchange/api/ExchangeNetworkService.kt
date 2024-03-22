package com.ithirteeng.secondpatternsclientproject.data.exchange.api

import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.CurrencyRate
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeNetworkService {

    @GET("XML_daily.asp")
    suspend fun getDailyRates(): CurrencyRate
}