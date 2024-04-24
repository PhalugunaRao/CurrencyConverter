package com.currency.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.currency.db.AppDatabase
import com.currency.db.entity.ExchangeRate
import com.currency.repo.ExchangeRateRepository
import com.currency.api.ExchangeRatesApi
import com.currency.util.CurrencyUtils
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRateViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: ExchangeRateRepository
    val exchangeRates: LiveData<List<ExchangeRate>>

    init {
        val db = AppDatabase.getInstance(application)
        val api = Retrofit.Builder()
            .baseUrl("http://api.exchangeratesapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .build()
            .create(ExchangeRatesApi::class.java)
        repository = ExchangeRateRepository(api, db.exchangeRateDao())
        exchangeRates = repository.exchangeRates
        //fetchExchangeRates("2f18edc1b984a6fe948b6d40deee0d43", "EUR")

    }


    fun fetchExchangeRates( base: String) {
        viewModelScope.launch {
            repository.fetchExchangeRates(CurrencyUtils.API_KEY, base)
        }
    }
}