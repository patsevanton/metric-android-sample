package com.example.metricdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.metricdemo.ui.theme.MetricDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SentryManager.getStoredDsn(this)?.let { dsn ->
            SentryManager.init(this, dsn)
        }
        setContent {
            MetricDemoTheme {
                App()
            }
        }
    }
}
