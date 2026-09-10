package com.example.metricdemo

import android.content.Context
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid

object SentryManager {

    private const val PREFS = "metric_demo_prefs"
    private const val KEY_DSN = "dsn"

    const val RELEASE = "metric-demo@1.0.0"
    const val ENVIRONMENT = "demo"

    fun isInitialized(): Boolean = Sentry.isEnabled()

    fun getStoredDsn(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_DSN, null)

    fun init(context: Context, dsn: String) {
        if (dsn.isBlank()) return

        SentryAndroid.init(context) { options ->
            options.dsn = dsn.trim()
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

        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_DSN, dsn.trim())
            .apply()
    }

    fun reset(context: Context) {
        Sentry.close()
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_DSN)
            .apply()
    }
}
