package com.example.metricdemo

import io.sentry.Sentry
import io.sentry.TransactionOptions
import io.sentry.okhttp.SentryOkHttpEventListener
import io.sentry.okhttp.SentryOkHttpInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object Traces {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(SentryOkHttpInterceptor())
        .eventListener(SentryOkHttpEventListener())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private fun bindOptions() = TransactionOptions().apply { isBindToScope = true }

    suspend fun manualTransaction() {
        val transaction = Sentry.startTransaction("manual-operation", "task", bindOptions())
        try {
            val child = transaction.startChild("worker", "compute")
            doWork(150)
            child.finish()
            val child2 = transaction.startChild("worker", "validate")
            doWork(100)
            child2.finish()
        } finally {
            transaction.finish()
        }
    }

    suspend fun nestedSpans() {
        val transaction = Sentry.startTransaction("nested-operation", "task", bindOptions())
        try {
            val outer = transaction.startChild("db", "query")
            doWork(80)
            val inner = outer.startChild("db", "query.row")
            doWork(60)
            inner.finish()
            val inner2 = outer.startChild("db", "query.map")
            doWork(40)
            inner2.finish()
            outer.finish()
        } finally {
            transaction.finish()
        }
    }

    suspend fun slowOperation() {
        val transaction = Sentry.startTransaction("slow-operation", "task", bindOptions())
        try {
            val span = transaction.startChild("sleep", "blocking")
            doWork(2000)
            span.finish()
        } finally {
            transaction.finish()
        }
    }

    suspend fun httpRequest(url: String): String = withContext(Dispatchers.IO) {
        val transaction = Sentry.startTransaction("http-request", "http.client", bindOptions())
        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            response.use {
                val body = it.body?.string()?.take(200) ?: ""
                "HTTP ${it.code}: $body"
            }
        } catch (e: Exception) {
            "Ошибка запроса: ${e.message}"
        } finally {
            transaction.finish()
        }
    }

    fun customSpan() {
        val parent = Sentry.getSpan()
        val span = if (parent != null) {
            parent.startChild("custom", "manual-span")
        } else {
            Sentry.startTransaction("custom-standalone", "task", bindOptions())
        }
        doWork(120)
        span.finish()
    }

    private fun doWork(ms: Long) {
        try {
            Thread.sleep(ms)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}
