package com.ithirteeng.secondpatternsclientproject.common.uikit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WideButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp
        )
    ) {
        Text(text = text)
    }
}