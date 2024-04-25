package com.currency.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.currency.R
import com.currency.databinding.ChartLayoutBinding
import com.currency.util.CurrencyUtils
import com.currency.viewmodel.CurrencyRatesViewModel
import com.currency.viewmodel.ExchangeRateViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale

class VisualisedActivity : AppCompatActivity() {
    lateinit var binding: ChartLayoutBinding
    private lateinit var baseCurrency: String
    private lateinit var viewModel: CurrencyRatesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.chart_layout)
        viewModel = ViewModelProvider(this).get(CurrencyRatesViewModel::class.java)
        binding.lifecycleOwner = this
        baseCurrency = CurrencyUtils.baseCurrencies[0]
        setupSpinners()

        viewModel.exchangeRates.observe(this, Observer { response ->
            // Update UI with response data
            //{"success":false,"error":{"code":105,"type":"function_access_restricted","info":"Access Restricted - Your current Subscription Plan does not support this API Function."}}
            println("response====$response")
        })

        // While calling api throwing error free acount won't support so handle static data api call added need to update my static data if api will work
        val jsonString = "{ \"success\": true, \"timeseries\": true, \"start_date\": \"2012-05-01\", \"end_date\": \"2012-05-07\", \"base\": \"EUR\", \"rates\": { \"2012-05-01\":{ \"USD\": 1.322891, \"AUD\": 1.278047, \"CAD\": 1.302303 }, \"2012-05-02\": { \"USD\": 1.315066, \"AUD\": 1.274202, \"CAD\": 1.299083 }, \"2012-05-03\": { \"USD\": 1.314491, \"AUD\": 1.280135, \"CAD\": 1.296868 }, \"2012-05-04\": { \"USD\": 1.514491, \"AUD\": 1.280135, \"CAD\": 1.296868 }, \"2012-05-05\": { \"USD\": 1.614491, \"AUD\": 1.280135, \"CAD\": 1.296868 }, \"2012-05-06\": { \"USD\": 1.714491, \"AUD\": 1.280135, \"CAD\": 1.296868 }, \"2012-05-07\": { \"USD\": 1.314491, \"AUD\": 1.280135, \"CAD\": 1.296868 } } }"
        val jsonObject = JSONObject(jsonString)
        val rates = jsonObject.getJSONObject("rates")

        val entries = ArrayList<Entry>()
        val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        for (i in 1..7) {
            val dateStr = "2012-05-0$i"
            val rate = rates.getJSONObject(dateStr).getDouble("USD").toFloat()
            entries.add(Entry(i.toFloat(), rate))
            dates.add(dateStr)
        }

        val dataSet = LineDataSet(entries, "Exchange Rate (USD)")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)

        binding.chart.data = lineData
        binding.chart.description.isEnabled = false
        binding.chart.setTouchEnabled(true)
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(true)
        binding.chart.setPinchZoom(true)

        val xAxis: XAxis =  binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = XAxisValueFormatter(dates)
        xAxis.labelRotationAngle = -45f

        val leftAxis: YAxis =  binding.chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.valueFormatter = YAxisValueFormatter()

        val rightAxis: YAxis =  binding.chart.axisRight
        rightAxis.isEnabled = false

        binding.chart.invalidate()
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
                viewModel.fetchExchangeRates("2012-05-01", "2012-05-25",baseCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}

class XAxisValueFormatter(private val values: List<String>) : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val index = value.toInt()
        return if (index >= 0 && index < values.size) {
            values[index]
        } else {
            ""
        }
    }
}

class YAxisValueFormatter : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return String.format("%.2f", value) // Format the Y-axis value as desired
    }
}