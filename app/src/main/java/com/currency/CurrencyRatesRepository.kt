import android.util.Log
import com.currency.CurrencyRatesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRatesRepository {
    private val service: CurrencyRatesService = Retrofit.Builder()
        .baseUrl("http://api.exchangeratesapi.io/")
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyRatesService::class.java)

    suspend fun getCurrencyRates(baseCurrency: String): Map<String, Double>? {
        return try {
            val response = service.getLatestRates(baseCurrency, "2f18edc1b984a6fe948b6d40deee0d43")
            response.rates
        } catch (e: Exception) {
            null
        }
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
