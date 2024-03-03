package com.ithirteeng.secondpatternsclientproject.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ithirteeng.secondpatternsclientproject.common.navigation.graph.common.AppNavHost
import com.ithirteeng.secondpatternsclientproject.common.uikit.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                rememberSystemUiController().setStatusBarColor(MaterialTheme.colorScheme.surface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navHostController = rememberNavController()
                    AppNavHost(navController = navHostController)
                }
            }
        }
    }
}