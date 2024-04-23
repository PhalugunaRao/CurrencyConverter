package com.currency

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String,
        @Query("access_key") apiKey: String
    ): CurrencyResponse
}