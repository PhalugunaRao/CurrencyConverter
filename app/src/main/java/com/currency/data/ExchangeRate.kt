package com.currency.data

data class ExchangeRate(
    val date: String,
    val usdRate: Double,
    val audRate: Double,
    val cadRate: Double
)
