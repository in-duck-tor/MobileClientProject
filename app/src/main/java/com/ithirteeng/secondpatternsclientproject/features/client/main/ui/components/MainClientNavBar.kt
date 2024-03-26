package com.ithirteeng.secondpatternsclientproject.features.client.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEvent
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientState

@Composable
fun MainClientNavBar(
    state: MainClientState.Content,
    eventListener: (MainClientEvent) -> Unit,
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        items(items = state.tabs) { tab ->
            MainClientTab(
                tab = tab,
                onClick = {
                    eventListener(MainClientEvent.Ui.TabClick(tab))
                },
                isSelected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}