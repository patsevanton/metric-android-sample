package com.example.metricdemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScaffold(title: String, onBack: () -> Unit, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("←")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            content()
        }
    }
}

@Composable
fun DemoButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
    ) {
        Text(text)
    }
}

@Composable
fun ErrorsScreen(onBack: () -> Unit) {
    DemoScaffold(title = stringResource(R.string.category_errors), onBack = onBack) {
        DemoButton(stringResource(R.string.errors_capture_exception)) {
            Demos.captureCaughtException()
        }
        DemoButton(stringResource(R.string.errors_uncaught_crash)) {
            Demos.captureUncaughtCrash()
        }
        DemoButton(stringResource(R.string.errors_capture_message)) {
            Demos.captureMessage()
        }
        DemoButton(stringResource(R.string.errors_warning)) {
            Demos.captureWarning()
        }
        DemoButton(stringResource(R.string.errors_custom_message)) {
            Demos.captureCustomMessageException()
        }
        DemoButton(stringResource(R.string.errors_log_exception)) {
            Demos.captureWithLog()
        }
    }
}
