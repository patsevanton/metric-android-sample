package com.example.metricdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SetupScreen(onConnected: () -> Unit) {
    val context = LocalContext.current
    var dsn by remember { mutableStateOf(SentryManager.getStoredDsn(context) ?: "") }
    var status by remember { mutableStateOf(SentryManager.isInitialized()) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.setup_title),
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = stringResource(R.string.setup_hint_text),
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedTextField(
                value = dsn,
                onValueChange = { dsn = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.setup_dsn_label)) },
                placeholder = { Text(stringResource(R.string.setup_dsn_hint)) },
                singleLine = true,
            )
            Button(
                onClick = {
                    if (dsn.isNotBlank()) {
                        SentryManager.init(context, dsn)
                        status = SentryManager.isInitialized()
                        onConnected()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = dsn.isNotBlank(),
            ) {
                Text(stringResource(R.string.setup_connect))
            }
            Button(
                onClick = {
                    SentryManager.reset(context)
                    dsn = ""
                    status = false
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.setup_reset))
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (status) stringResource(R.string.setup_connected)
                    else stringResource(R.string.setup_not_connected),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
