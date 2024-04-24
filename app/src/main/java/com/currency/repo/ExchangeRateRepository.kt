package com.currency.repo

import androidx.lifecycle.LiveData
import com.currency.api.ExchangeRatesApi
import com.currency.data.ExchangeRatesResponse
import com.currency.db.dao.ExchangeRateDao
import com.currency.db.entity.ExchangeRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class ExchangeRateRepository(private val api: ExchangeRatesApi, private val dao: ExchangeRateDao) {

    val exchangeRates: LiveData<List<ExchangeRate>> = dao.getAllRates()

    suspend fun fetchExchangeRates(apiKey: String, base: String) {
        withContext(Dispatchers.IO) {
            try {
                val response: Response<ExchangeRatesResponse> = api.getLatestRates(apiKey, base)
                if (response.isSuccessful) {
                    val ratesResponse = response.body()
                    ratesResponse?.let { response ->
                        val ratesList = mutableListOf<ExchangeRate>()
                        response.rates.forEach { (currency, rate) ->
                            ratesList.add(ExchangeRate(0, currency, rate))
                        }
                        dao.insertAll(ratesList)
                    }
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}