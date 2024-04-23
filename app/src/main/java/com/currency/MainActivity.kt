package com.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CurrencyRatesViewModel
    lateinit var binding: ActivityMainBinding
    private var baseCurrency: String = "EUR"
    private lateinit var currencyRatesAdapter: CurrencyRatesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CurrencyRatesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Example usage: Fetch currency rates for EUR
        viewModel.fetchCurrencyRates("EUR")

        viewModel.currencyRates.observe(this) { rates ->
            println("rates===$rates")
            // Handle the fetched rates
            rates?.let {
                //displayRates(it)
                setupSpinner(it)
                //setupRecyclerView(it)
            }
        }

        binding.buttonConvert.setOnClickListener {
            val amountString = binding.editTextAmount.text.toString()
            if (amountString.isNotEmpty()) {
                val amount = amountString.toDouble()
                val targetCurrency = binding.spinnerCurrency.selectedItem.toString()
                convertCurrency(amount, targetCurrency)
            }
        }
    }

    private fun setupRecyclerView(rates: Map<String, Double>) {
        currencyRatesAdapter = CurrencyRatesAdapter(rates)
        binding.recyclerViewCurrencyRates.adapter = currencyRatesAdapter
        binding.recyclerViewCurrencyRates.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSpinner(rates: Map<String, Double>) {
        val currencies = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrency.adapter = adapter
    }

    private fun convertCurrency(amount: Double, targetCurrency: String) {
        viewModel.currencyRates.value?.let { rates ->
            val rate = rates[targetCurrency]
            if (rate != null) {
                val convertedAmount = amount * rate
                val decimalFormat = DecimalFormat("#.##")
                val resultText = "${decimalFormat.format(amount)} ${baseCurrency} = " +
                        "${decimalFormat.format(convertedAmount)} ${targetCurrency}"
                binding.textViewResult.text = resultText
            } else {
                binding.textViewResult.text = "Conversion rate not available."
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