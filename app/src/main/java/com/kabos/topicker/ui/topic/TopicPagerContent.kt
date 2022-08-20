package com.kabos.topicker.ui.topic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.model.domain.TopicUiState
import com.kabos.topicker.ui.theme.TopickerTheme

@ExperimentalPagerApi
@Composable
fun TopicPagerContent(
    pagerState: PagerState,
    contents: List<TopicUiState>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        PagerTopAppBar(
            modifier = Modifier
                .wrapContentSize()
                .statusBarsPadding()
        )
        TopicPager(
            pagerState = pagerState,
            pageCount = contents.size,
            modifier = Modifier.fillMaxSize(),
            pagerColors = contents.map { it.color }
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(120.dp))
                TopicCard(text = contents[page].title)
                Spacer(modifier = Modifier.height(30.dp))
                Row() {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.ArrowForward , contentDescription = "スキップ")
                        Text(text = "スキップ")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.ThumbUp, contentDescription = "いいね")
                        Text(text = "会話した")
                    }
                }
            }
        }
    }
}

@Composable
fun TopicCard(text: String) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.padding(vertical = 48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopicCard() {
    TopickerTheme {
        TopicCard(text = "おもしろい話")
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun PreviewTopicPagerContent() {
    val pagerState = rememberPagerState()
    val content = listOf(
        TopicUiState("面白い話", Color.Cyan),
        TopicUiState("悲しい話", Color.Green),
        TopicUiState("たらればの話", Color.Yellow)
    )
    TopickerTheme {
        TopicPagerContent(pagerState = pagerState, contents = content)
    }
}
