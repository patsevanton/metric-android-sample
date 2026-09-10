package com.example.metricdemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@Composable
fun AdvancedScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()

    DemoScaffold(title = stringResource(R.string.category_advanced), onBack = onBack) {
        DemoButton(stringResource(R.string.advanced_custom_span)) {
            Traces.customSpan()
        }
        DemoButton(stringResource(R.string.advanced_fingerprint)) {
            Demos.captureWithFingerprint()
        }
        DemoButton(stringResource(R.string.advanced_sampling)) {
            Demos.captureMessage()
        }
        DemoButton(stringResource(R.string.advanced_before_send)) {
            Demos.captureModifiedByBeforeSend()
        }
        DemoButton(stringResource(R.string.advanced_drop)) {
            Demos.captureDroppedByBeforeSend()
        }
    }
}
