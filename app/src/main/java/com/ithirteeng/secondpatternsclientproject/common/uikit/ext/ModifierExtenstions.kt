package com.ithirteeng.secondpatternsclientproject.common.uikit.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

fun Modifier.circleRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = false, radius = 40.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}