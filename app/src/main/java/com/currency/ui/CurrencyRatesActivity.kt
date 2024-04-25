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

        baseCurrency = CurrencyUtils.baseCurrencies[0]
        setupSpinners()
        binding.refresh.setOnClickListener {
            viewModel.fetchExchangeRates(baseCurrency)
        }

        adapter = ExchangeRateAdapter()
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerDrawable?.let { dividerItemDecoration.setDrawable(it) }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)
        viewModel.exchangeRates.observe(this, Observer { rates ->
            adapter.submitList(rates)
        })
    }

    private fun setupSpinners() {
        val baseCurrencyAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_layout,
            CurrencyUtils.baseCurrencies
        )
        binding.spinnerBaseCurrency.adapter = baseCurrencyAdapter

        binding.spinnerBaseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrency = CurrencyUtils.baseCurrencies[position]
                viewModel.fetchExchangeRates(baseCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Not implement
            }
        }
    }
}