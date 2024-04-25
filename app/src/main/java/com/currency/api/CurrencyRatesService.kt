package com.currency.api

import com.currency.data.CurrencyResponse
import com.currency.data.CurrencyWeekResponse
import com.currency.data.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String,
        @Query("access_key") apiKey: String
    ): CurrencyResponse

    @GET("timeseries")
    suspend fun getExchangeWeeks(
        @Query("access_key") accessKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String
    ): CurrencyWeekResponse


}