package com.currency.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.currency.R
import com.currency.databinding.SplashScreenBinding
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_DELAY: Long = 3000 // 3 seconds
    private val splashCoroutineScope = CoroutineScope(Dispatchers.Main)
    lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)
        splashCoroutineScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        val intent = Intent(this@SplashScreenActivity, CurrencyRootActivity::class.java)
        startActivity(intent)
        finish() // close this activity
    }

    override fun onDestroy() {
        splashCoroutineScope.cancel()
        super.onDestroy()
    }
}