package com.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.currency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CurrencyRatesViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CurrencyRatesViewModel::class.java)

        // Example usage: Fetch currency rates for EUR
        viewModel.fetchCurrencyRates("EUR")

        viewModel.currencyRates.observe(this) { rates ->
            println("rates===$rates")
            // Handle the fetched rates
            rates?.let {
                displayRates(it)
            }
        }
    }
    private fun displayRates(rates: Map<String, Double>) {
        // Display the rates in your UI
        // For example, you can use a RecyclerView or TextViews
        val ratesText = StringBuilder()
        for ((currency, rate) in rates) {
            ratesText.append("$currency: $rate\n")
        }
        binding.textViewResult.text = ratesText.toString()
    }
}