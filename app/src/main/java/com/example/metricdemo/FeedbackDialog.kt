package com.example.metricdemo

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun FeedbackDialog(
    eventId: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.feedback_dialog)) },
        text = { Text("Событие $eventId отправлено. Спасибо за обратную связь!") },
        confirmButton = {
            TextButton(onClick = {
                Demos.captureUserFeedback(eventId)
                onDismiss()
            }) {
                Text("Отправить фидбек")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        },
    )
}
