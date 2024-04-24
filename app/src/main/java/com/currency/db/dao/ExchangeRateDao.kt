package com.currency.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.db.entity.ExchangeRate

@Dao
interface ExchangeRateDao {
    @Query("SELECT * FROM ExchangeRate")
    fun getAllRates(): LiveData<List<ExchangeRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rates: List<ExchangeRate>)
}