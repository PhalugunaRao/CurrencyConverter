package com.currency.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeRate(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val currency: String,
    val rate: Double
)
