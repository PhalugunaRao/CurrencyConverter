package com.currency.api

import com.currency.data.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String,
        @Query("access_key") apiKey: String
    ): CurrencyResponse
}