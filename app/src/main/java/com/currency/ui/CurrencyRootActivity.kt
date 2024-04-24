package com.currency.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.currency.R
import com.currency.databinding.TabActivityBinding

class CurrencyRootActivity : AppCompatActivity() {
    lateinit var binding: TabActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.tab_activity)
        binding.currencyRates.setOnClickListener {
            val intent = Intent(this@CurrencyRootActivity, CurrencyRatesActivity::class.java)
            startActivity(intent)
        }
        binding.currencyConverter.setOnClickListener {
            val intent = Intent(this@CurrencyRootActivity, CurrencyConvertActivity::class.java)
            startActivity(intent)
        }
        binding.currencyVisualised.setOnClickListener {
            val intent = Intent(this@CurrencyRootActivity, VisualisedActivity::class.java)
            startActivity(intent)
        }

    }

}