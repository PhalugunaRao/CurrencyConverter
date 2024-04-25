package com.currency.data

data class CurrencyWeekResponse(
    val success: Boolean,
    val timeseries: Boolean,
    val start_date: String,
    val end_date: String,
    val base: String,
    val rates: Map<String, CurrencyDayEntry>
)
