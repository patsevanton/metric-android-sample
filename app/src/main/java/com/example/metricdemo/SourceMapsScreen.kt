package com.example.metricdemo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@Composable
fun SourceMapsScreen(onBack: () -> Unit) {
    DemoScaffold(title = stringResource(R.string.category_sourcemaps), onBack = onBack) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.sourcemaps_crash_note),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        DemoButton(stringResource(R.string.sourcemaps_crash_release)) {
            Demos.captureUncaughtCrash()
        }
    }
}
