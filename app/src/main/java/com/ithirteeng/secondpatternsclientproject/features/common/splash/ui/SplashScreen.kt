package com.ithirteeng.secondpatternsclientproject.features.common.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.SplashViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEffect
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    navigateToMainScreen: (token: String) -> Unit,
    navigateToAuthorizationScreen: () -> Unit,
) {
    LaunchedEffect(null) {
        viewModel.processEvent(SplashEvent.Init)

        observeEffects(
            viewModel = viewModel,
            navigateToMainScreen = navigateToMainScreen,
            navigateToAuthorizationScreen = navigateToAuthorizationScreen,
        )
    }

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.image_app_icon),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape),
        )
    }

}

private suspend fun observeEffects(
    viewModel: SplashViewModel,
    navigateToMainScreen: (token: String) -> Unit,
    navigateToAuthorizationScreen: () -> Unit,
) {
    viewModel.effectsFlow.collect { effect ->
        when (effect) {
            is SplashEffect.NavigateToMainScreen -> navigateToMainScreen(effect.token)
            is SplashEffect.NavigateToAuthorizationScreen -> navigateToAuthorizationScreen()
        }
    }
}