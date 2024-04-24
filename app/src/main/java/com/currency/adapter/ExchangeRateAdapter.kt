package com.currency.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.currency.R
import com.currency.db.entity.ExchangeRate

class ExchangeRateAdapter : ListAdapter<ExchangeRate, ExchangeRateAdapter.ExchangeRateViewHolder>(
    ExchangeRateDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rate, parent, false)
        return ExchangeRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        val exchangeRate = getItem(position)
        holder.bind(exchangeRate)
    }

    inner class ExchangeRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyTextView: TextView = itemView.findViewById(R.id.currencyTextView)
        private val rateTextView: TextView = itemView.findViewById(R.id.rateTextView)

        fun bind(exchangeRate: ExchangeRate) {
            currencyTextView.text = exchangeRate.currency
            rateTextView.text = exchangeRate.rate.toString()
        }
    }

    class ExchangeRateDiffCallback : DiffUtil.ItemCallback<ExchangeRate>() {
        override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
            return oldItem == newItem
        }
    }
}