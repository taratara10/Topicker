package com.kabos.topicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopickerApp(
                setStatusBarColor = { color -> setStatusBarColor(color)}
            )
        }
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = color
        window.navigationBarColor = color
    }
}

