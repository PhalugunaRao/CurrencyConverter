package com.currency.viewmodel
import com.currency.repo.CurrencyRatesRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyRatesViewModel : ViewModel() {
    private val repository = CurrencyRatesRepository()
    private val _currencyRates = MutableLiveData<Map<String, Double>?>()
    val currencyRates: MutableLiveData<Map<String, Double>?> = _currencyRates

    fun fetchCurrencyRates(baseCurrency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val rates = repository.getCurrencyRates(baseCurrency)
            _currencyRates.postValue(rates)
        }
    }
}