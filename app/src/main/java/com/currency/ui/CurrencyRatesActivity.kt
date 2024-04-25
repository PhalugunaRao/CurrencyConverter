package com.currency.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.util.CurrencyUtils
import com.currency.adapter.ExchangeRateAdapter
import com.currency.viewmodel.ExchangeRateViewModel
import com.currency.R
import com.currency.databinding.ActivityMainViewBinding

class CurrencyRatesActivity:  AppCompatActivity() {

    private lateinit var viewModel: ExchangeRateViewModel
    private lateinit var adapter: ExchangeRateAdapter
    lateinit var binding: ActivityMainViewBinding
    private lateinit var baseCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_view)
        // Initialize ViewModel
        baseCurrency = CurrencyUtils.baseCurrencies[0]
        setupSpinners()

        // Initialize RecyclerView and its adapter
        adapter = ExchangeRateAdapter()

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider)

// Create an item decoration with the divider drawable
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerDrawable?.let { dividerItemDecoration.setDrawable(it) }

        // Set RecyclerView's layout manager and adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)


        viewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)



        // Observe LiveData from ViewModel
        viewModel.exchangeRates.observe(this, Observer { rates ->
            // Update RecyclerView's data with the new list of exchange rates
            adapter.submitList(rates)
        })

        // Fetch exchange rates when needed
        //viewModel.fetchExchangeRates("2f18edc1b984a6fe948b6d40deee0d43", "EUR")
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
                viewModel.fetchExchangeRates(baseCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}