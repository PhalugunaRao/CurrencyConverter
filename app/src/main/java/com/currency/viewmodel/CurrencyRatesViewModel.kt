package com.currency.viewmodel
import androidx.lifecycle.LiveData
import com.currency.repo.CurrencyRatesRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.data.CurrencyWeekResponse
import com.currency.data.ExchangeRate
import com.currency.util.CurrencyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CurrencyRatesViewModel : ViewModel() {
    private val repository = CurrencyRatesRepository()
    private val _currencyRates = MutableLiveData<Map<String, Double>?>()
    val currencyRates: MutableLiveData<Map<String, Double>?> = _currencyRates

    private val _exchangeRates = MutableLiveData<CurrencyWeekResponse?>()
    val exchangeRates: MutableLiveData<CurrencyWeekResponse?> = _exchangeRates



    fun fetchCurrencyRates(baseCurrency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val rates = repository.getCurrencyRates(baseCurrency)
            _currencyRates.postValue(rates)
        }
    }

    fun fetchExchangeRates( startDate: String, endDate: String,baseCurrency:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getExchangeRates(CurrencyUtils.API_KEY, startDate, endDate,baseCurrency)
            _exchangeRates.postValue(result)
        }
    }
}
