package com.currency

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.currency.databinding.ConvertLayoutBinding
import java.text.DecimalFormat

class ConvertActivity2 : AppCompatActivity() {
    private lateinit var viewModel: CurrencyRatesViewModel
    lateinit var binding: ConvertLayoutBinding
    private lateinit var baseCurrency: String
    private lateinit var targetCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.convert_layout)

        viewModel = ViewModelProvider(this).get(CurrencyRatesViewModel::class.java)
        binding.lifecycleOwner = this
        baseCurrency = CurrencyUtils.baseCurrencies[0] // Set default base currency
        targetCurrency = CurrencyUtils.targetCurrencies[0]

        setupSpinners()

        binding.buttonConvert.setOnClickListener {
            val amountString = binding.editTextAmount.text.toString()
            if (amountString.isNotEmpty()) {
                val amount = amountString.toDouble()
                convertCurrency(amount)
            }
        }

    }

    private fun setupSpinners() {
        val baseCurrencyAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_layout,  // Use custom layout
            CurrencyUtils.baseCurrencies
        )
        binding.spinnerBaseCurrency.adapter = baseCurrencyAdapter



        binding.spinnerBaseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrency = CurrencyUtils.baseCurrencies[position]
                viewModel.fetchCurrencyRates(baseCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val targetCurrencyAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_layout,  // Use custom layout
            CurrencyUtils.targetCurrencies
        )
        binding.spinnerTargetCurrency.adapter = targetCurrencyAdapter

        binding.spinnerTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                targetCurrency = CurrencyUtils.targetCurrencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun convertCurrency(amount: Double) {
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