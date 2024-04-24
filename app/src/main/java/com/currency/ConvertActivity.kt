package com.currency

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.databinding.ActivityMainBinding
import java.text.DecimalFormat

import androidx.appcompat.app.AppCompatActivity
import com.currency.databinding.ConvertLayoutBinding


class ConvertActivity: AppCompatActivity() {
    private lateinit var viewModel: CurrencyRatesViewModel
    lateinit var binding: ConvertLayoutBinding
    private lateinit var baseCurrency: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.convert_layout)

        viewModel = ViewModelProvider(this).get(CurrencyRatesViewModel::class.java)
        binding.lifecycleOwner = this
        setupSpinners()

        binding.buttonConvert.setOnClickListener {
            val amountString = binding.editTextAmount.text.toString()
            if (amountString.isNotEmpty()) {
                val amount = amountString.toDouble()
                val baseCurrency = binding.spinnerBaseCurrency.selectedItem.toString()
                val targetCurrency = binding.spinnerTargetCurrency.selectedItem.toString()
                convertCurrency(amount, baseCurrency, targetCurrency)
            }
        }
    }

    private fun setupSpinners() {
        // Set up base currency spinner
        viewModel.currencyRates.observe(this) { rates ->
            println("===$rates")
            rates?.let {
                val currencies = it.keys.toList()
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerBaseCurrency.adapter = adapter
            }
        }

        // Set up target currency spinner
        viewModel.currencyRates.observe(this) { rates ->
            println("333===$rates")
            rates?.let {
                val currencies = it.keys.toList()
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerTargetCurrency.adapter = adapter
            }
        }

        binding.spinnerBaseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                baseCurrency = binding.spinnerBaseCurrency.selectedItem.toString()
                viewModel.fetchCurrencyRates(baseCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun convertCurrency(amount: Double, baseCurrency: String, targetCurrency: String) {
        viewModel.currencyRates.value?.let { rates ->
            val baseRate = rates[baseCurrency]
            val targetRate = rates[targetCurrency]
            if (baseRate != null && targetRate != null) {
                val convertedAmount = amount * (targetRate / baseRate)
                val decimalFormat = DecimalFormat("#.##")
                val resultText = "${decimalFormat.format(amount)} $baseCurrency = " +
                        "${decimalFormat.format(convertedAmount)} $targetCurrency"
                binding.textViewResult.text = resultText
            } else {
                binding.textViewResult.text = "Conversion rate not available."
            }
        }
    }
}