package com.example.metricdemo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.sentry.Sentry
import io.sentry.SentryLevel

@Composable
fun ReleasesScreen(onBack: () -> Unit) {
    DemoScaffold(title = stringResource(R.string.category_releases), onBack = onBack) {
        Text(stringResource(R.string.releases_release) + ": " + SentryManager.RELEASE)
        Text(stringResource(R.string.releases_environment) + ": " + SentryManager.ENVIRONMENT)
        DemoButton(stringResource(R.string.releases_restart_session)) {
            Sentry.startSession()
        }
        DemoButton(stringResource(R.string.releases_end_session)) {
            Sentry.endSession()
        }
        DemoButton(stringResource(R.string.releases_capture_message)) {
            Sentry.configureScope { scope ->
                scope.setTag("release", SentryManager.RELEASE)
            }
            Sentry.captureMessage("Событие, привязанное к релизу", SentryLevel.INFO)
        }
    }
}
