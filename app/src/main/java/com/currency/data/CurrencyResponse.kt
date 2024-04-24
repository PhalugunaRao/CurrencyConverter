package com.currency.data

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double>
)
