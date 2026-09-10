package com.example.metricdemo

import io.sentry.Attachment
import io.sentry.Breadcrumb
import io.sentry.Hint
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.protocol.Feedback
import io.sentry.protocol.SentryId
import io.sentry.protocol.User

object Demos {

    fun captureCaughtException() {
        try {
            throw IllegalStateException("Перехваченное исключение в Metric Demo")
        } catch (e: Exception) {
            Sentry.captureException(e)
        }
    }

    fun captureUncaughtCrash() {
        throw RuntimeException("Необработанный крэш из Metric Demo")
    }

    fun captureMessage() {
        Sentry.captureMessage("Информационное сообщение из Metric Demo", SentryLevel.INFO)
    }

    fun captureWarning() {
        Sentry.captureMessage("Предупреждение из Metric Demo", SentryLevel.WARNING)
    }

    fun captureCustomMessageException() {
        Sentry.captureException(
            IllegalStateException("Исключение с кастомным сообщением: заказ #1234 не найден")
        )
    }

    fun captureWithLog() {
        try {
            Thread.sleep(120)
            throw java.io.IOException("Сетевая ошибка при загрузке данных")
        } catch (e: Exception) {
            Sentry.captureException(e)
        }
    }

    fun addBreadcrumb(message: String, category: String, level: SentryLevel) {
        val crumb = Breadcrumb().apply {
            this.message = message
            this.category = category
            this.level = level
            setData("source", "manual-demo")
        }
        Sentry.addBreadcrumb(crumb)
    }

    fun setTag(key: String, value: String) {
        Sentry.setTag(key, value)
    }

    fun setExtra(key: String, value: String) {
        Sentry.setExtra(key, value)
    }

    fun setUser(id: String, email: String, username: String) {
        val user = User().apply {
            this.id = id
            this.email = email
            this.username = username
        }
        Sentry.setUser(user)
    }

    fun setContext() {
        Sentry.configureScope { scope ->
            scope.setContexts(
                "checkout",
                mapOf("step" to "payment", "attempt" to 2, "fast_track" to true)
            )
        }
    }

    fun captureWithScope() {
        Sentry.withScope { scope ->
            scope.setTag("screen", "context-demo")
            scope.setExtra("order_id", "ord_789")
            scope.level = SentryLevel.INFO
            Sentry.captureMessage("Событие с scope-контекстом", SentryLevel.INFO)
        }
    }

    fun captureWithFingerprint() {
        Sentry.withScope { scope ->
            scope.fingerprint = listOf("payment-gateway", "timeout")
            Sentry.captureMessage("Таймаут платёжного шлюза", SentryLevel.ERROR)
        }
    }

    fun captureModifiedByBeforeSend() {
        Sentry.captureMessage("Исходное сообщение", SentryLevel.INFO)
    }

    fun captureDroppedByBeforeSend() {
        Sentry.captureMessage("SECRET: token=12345", SentryLevel.INFO)
    }

    fun captureWithAttachment(): String {
        val eventId = Sentry.captureMessage("Событие с вложением", SentryLevel.INFO)
        val attachment = Attachment(
            "Дополнительный контекст для диагностики\nВремя: ${System.currentTimeMillis()}".toByteArray(),
            "details.txt"
        )
        Sentry.captureEvent(
            io.sentry.SentryEvent().apply {
                message = io.sentry.protocol.Message().apply {
                    this.message = "Вложенный attachment-файл"
                }
                level = SentryLevel.INFO
            },
            Hint.withAttachment(attachment)
        )
        return eventId.toString()
    }

    fun captureUserFeedback(eventId: String) {
        val feedback = Feedback("Приложение зависло при оформлении заказа").apply {
            name = "Иван Петров"
            contactEmail = "ivan@example.com"
            setAssociatedEventId(SentryId(eventId))
        }
        Sentry.feedback().capture(feedback)
    }
}
