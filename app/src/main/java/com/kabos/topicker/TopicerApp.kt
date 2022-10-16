package com.kabos.topicker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kabos.topicker.navigation.TopicNavHost
import com.kabos.topicker.ui.theme.TopickerTheme

@ExperimentalPagerApi
@Composable
fun TopickerApp() {
    val navHostController = rememberNavController()
    TopickerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            TopicNavHost(navHostController = navHostController)
        }
    }
}
