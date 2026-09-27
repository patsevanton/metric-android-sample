package com.example.metricdemo

import android.content.Context
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid

object SentryManager {

    const val RELEASE = "metric-demo@1.0.0"
    const val ENVIRONMENT = "demo"

    fun init(context: Context) {
        val dsn = BuildConfig.METRIC_DSN.trim()
        if (dsn.isBlank()) return

        SentryAndroid.init(context) { options ->
            options.dsn = dsn
            options.release = RELEASE
            options.environment = ENVIRONMENT
            options.tracesSampleRate = 1.0
            options.isDebug = false
            options.setDiagnosticLevel(SentryLevel.INFO)

            options.beforeSend = io.sentry.SentryOptions.BeforeSendCallback { event, _ ->
                val message = event.message?.message ?: return@BeforeSendCallback event
                when {
                    message.contains("SECRET:") -> null
                    message.contains("Исходное сообщение") -> event.apply {
                        this.message?.message = "$message — изменено в beforeSend"
                    }
                    else -> event
                }
            }
        }
    }
}
