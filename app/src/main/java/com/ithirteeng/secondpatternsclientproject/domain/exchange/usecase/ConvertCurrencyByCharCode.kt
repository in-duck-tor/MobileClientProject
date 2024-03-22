package com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase

import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

class ConvertCurrencyByCharCode {

    operator fun invoke(
        currencyRates: List<Currency>,
        codeFrom: String,
        codeTo: String,
        value: Double,
    ): Result<Double> {
        val fromRate = currencyRates.findLast { it.charCode == codeFrom }?.value
        val toRate = currencyRates.findLast { it.charCode == codeTo }?.value
        return if (fromRate != null && toRate != null) {
            Result.success(
                value * fromRate / toRate
            )
        } else {
            Result.failure(NullPointerException("Currency does\'t exist"))
        }

    }
}