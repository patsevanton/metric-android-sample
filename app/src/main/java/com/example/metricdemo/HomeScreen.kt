package com.example.metricdemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

private data class Category(val route: String, val title: Int)

@Composable
fun HomeScreen(onNavigate: (String) -> Unit, onOpenSetup: () -> Unit) {
    val categories = listOf(
        Category(Routes.ERRORS, R.string.category_errors),
        Category(Routes.TRACES, R.string.category_traces),
        Category(Routes.RELEASES, R.string.category_releases),
        Category(Routes.SOURCEMAPS, R.string.category_sourcemaps),
        Category(Routes.CONTEXT, R.string.category_context),
        Category(Routes.FEEDBACK, R.string.category_feedback),
        Category(Routes.ADVANCED, R.string.category_advanced),
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.home_subtitle),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.height(16.dp))
            categories.forEach { category ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(category.route) },
                ) {
                    Text(
                        text = stringResource(category.title),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onOpenSetup) {
                Text(stringResource(R.string.setup_title))
            }
        }
    }
}
