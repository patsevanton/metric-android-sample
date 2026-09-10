package com.example.metricdemo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@Composable
fun TracesScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    val defaultUrl = stringResource(R.string.traces_http_default_url)
    var url by remember { mutableStateOf(defaultUrl) }
    var result by remember { mutableStateOf("") }

    DemoScaffold(title = stringResource(R.string.category_traces), onBack = onBack) {
        DemoButton(stringResource(R.string.traces_transaction)) {
            scope.launch { Traces.manualTransaction() }
        }
        DemoButton(stringResource(R.string.traces_nested_spans)) {
            scope.launch { Traces.nestedSpans() }
        }
        DemoButton(stringResource(R.string.traces_slow)) {
            scope.launch { Traces.slowOperation() }
        }
        Text(stringResource(R.string.traces_http_url_label))
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        DemoButton(stringResource(R.string.traces_http_run)) {
            scope.launch { result = Traces.httpRequest(url) }
        }
        if (result.isNotEmpty()) {
            Text(stringResource(R.string.traces_http_result, result))
        }
    }
}
