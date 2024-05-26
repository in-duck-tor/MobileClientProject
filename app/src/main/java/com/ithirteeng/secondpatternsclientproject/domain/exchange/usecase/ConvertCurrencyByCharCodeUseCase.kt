package com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase

import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

class ConvertCurrencyByCharCodeUseCase {

    operator fun invoke(
        currencyRates: List<Currency>,
        codeFrom: String,
        codeTo: String,
        value: Double,
    ): Result<Double> {
        var fromRate = currencyRates.findLast { it.charCode == codeFrom }?.value
        var toRate = currencyRates.findLast { it.charCode == codeTo }?.value

        if (codeTo == "RUB") {
            toRate = 1.0
        }
        if (codeFrom == "RUB") {
            fromRate = 1.0
        }

        return if (fromRate != null && toRate != null) {
            Result.success(value * fromRate / toRate)
        } else {
            Result.failure(NullPointerException("Currency $codeTo does\'t exist"))
        }
    }
}
