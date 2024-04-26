package com.ithirteeng.secondpatternsclientproject.app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ithirteeng.secondpatternsclientproject.common.navigation.graph.common.AppNavHost
import com.ithirteeng.secondpatternsclientproject.common.uikit.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val uri: Uri? = intent.data
//        Log.d("BULLSHIT", uri?.getQueryParameter("code").toString())

        setContent {
            val theme = viewModel.themeState.collectAsState().value
            AppTheme(theme) {
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