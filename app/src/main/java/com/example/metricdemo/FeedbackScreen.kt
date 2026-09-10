package com.example.metricdemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.sentry.Sentry
import io.sentry.SentryLevel

@Composable
fun FeedbackScreen(onBack: () -> Unit) {
    var lastEventId by remember { mutableStateOf<String?>(null) }

    DemoScaffold(title = stringResource(R.string.category_feedback), onBack = onBack) {
        DemoButton(stringResource(R.string.feedback_capture)) {
            val id = Sentry.captureMessage("Скриншот и событие", SentryLevel.INFO)
            lastEventId = id.toString()
        }
        DemoButton(stringResource(R.string.feedback_dialog)) {
            val id = Sentry.captureMessage("Событие для фидбека", SentryLevel.INFO)
            lastEventId = id.toString()
        }
        DemoButton(stringResource(R.string.feedback_attachment)) {
            Demos.captureWithAttachment()
        }
        lastEventId?.let { eventId ->
            FeedbackDialog(eventId = eventId, onDismiss = { lastEventId = null })
        }
    }
}
