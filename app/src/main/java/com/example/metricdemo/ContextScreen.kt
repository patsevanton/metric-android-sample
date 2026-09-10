package com.example.metricdemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.sentry.SentryLevel

@Composable
fun ContextScreen(onBack: () -> Unit) {
    DemoScaffold(title = stringResource(R.string.category_context), onBack = onBack) {
        DemoButton(stringResource(R.string.context_breadcrumb)) {
            Demos.addBreadcrumb("Пользователь открыл экран оплаты", "ui.action", SentryLevel.INFO)
        }
        DemoButton(stringResource(R.string.context_tag)) {
            Demos.setTag("plan", "pro")
        }
        DemoButton(stringResource(R.string.context_extra)) {
            Demos.setExtra("cart_items", "3")
        }
        DemoButton(stringResource(R.string.context_user)) {
            Demos.setUser("user-42", "ivan@example.com", "ivan_petrov")
        }
        DemoButton(stringResource(R.string.context_set_context)) {
            Demos.setContext()
        }
        DemoButton(stringResource(R.string.context_scope)) {
            Demos.captureWithScope()
        }
        DemoButton(stringResource(R.string.context_send)) {
            Demos.captureMessage()
        }
    }
}
