package com.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currency.databinding.ItemCurrencyRateBinding

class CurrencyRatesAdapter(private val currencyRates: Map<String, Double>) :
    RecyclerView.Adapter<CurrencyRatesAdapter.CurrencyRateViewHolder>() {

    class CurrencyRateViewHolder(private val binding: ItemCurrencyRateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: String, rate: Double) {
            binding.currency = currency
            binding.rate = rate.toString()
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyRateBinding.inflate(inflater, parent, false)
        return CurrencyRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        val currency = currencyRates.keys.toList()[position]
        val rate = currencyRates[currency] ?: 0.0
        holder.bind(currency, rate)
    }

    override fun getItemCount(): Int {
        return currencyRates.size
    }
}
