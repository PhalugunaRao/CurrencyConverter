package com.currency.api

import com.currency.data.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {
//    @GET("latest")
//    fun getLatestRates(
//        @Query("access_key") apiKey: String,
//        @Query("base") base: String,
//    ): Call<ExchangeRatesResponse>
    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String,
        @Query("base") base: String
    ): Response<ExchangeRatesResponse>
}