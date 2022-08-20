package com.kabos.topicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.ui.theme.TopickerTheme
import com.kabos.topicker.ui.topic.TopicPager

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val pagerState = rememberPagerState()
                    TopicPager(
                        pagerState = pagerState,
                        pageCount = 10,
                        modifier = Modifier,
                        pagerColors = listOf(Color.Blue, Color.Cyan)
                    ) { page ->
                        Text(text = "page is $page")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopickerTheme {
        Greeting("Android")
    }
}
