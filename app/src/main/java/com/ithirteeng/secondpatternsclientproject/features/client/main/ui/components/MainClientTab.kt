package com.ithirteeng.secondpatternsclientproject.features.client.main.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.common.uikit.ext.circleRippleClickable
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model.MainClientTab

@Composable
fun MainClientTab(
    tab: MainClientTab,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.circleRippleClickable(
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val color = if (isSelected) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.primary
        }
        Icon(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(24.dp),
            contentDescription = null,
            tint = color,
            imageVector = ImageVector.vectorResource(id = tab.iconResource)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = tab.titleResource),
            style = MaterialTheme.typography.titleSmall,
            softWrap = false,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}
