package com.davidsantiagoiriarte.giphy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidsantiagoiriarte.presentation.util.MAIN_ACTIVITY_CLASS_NAME
import kotlinx.coroutines.*

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main + Job()).launch {
            delay(SPLASH_DELAY_MILLISECONDS)
            val intent = Intent().setClassName(
                this@LaunchActivity,
                MAIN_ACTIVITY_CLASS_NAME
            )
            startActivity(intent)
            finishAffinity()
        }

    }

    companion object {
        const val SPLASH_DELAY_MILLISECONDS: Long = 2000
    }
}