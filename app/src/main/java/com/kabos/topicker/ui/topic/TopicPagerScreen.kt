package com.kabos.topicker.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.ui.theme.TopickerTheme
import timber.log.Timber

@ExperimentalPagerApi
@Composable
fun TopicPagerScreen(
    pagerState: PagerState,
    viewModel: TopicViewModel = viewModel()
) {

    val topics by viewModel.topicUiState.collectAsState()

    SideEffect {
        Timber.d("--ss TopicPagerScreen Recomposition")
        Timber.d("--ss ${topics.size}")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopicPagerAppBar(
            modifier = Modifier
                .wrapContentSize()
                .statusBarsPadding()
        )
        TopicPager(
            pagerState = pagerState,
            pageCount = topics.size,
            modifier = Modifier.fillMaxSize(),
            pagerColors = topics.map { it.color }
        ) { page ->
            TopicContent(
                uiState = topics[page],
                onClickConversation = { viewModel.addTopic() },
                onClickSkip = { viewModel.updateConversationState() })
        }
    }
}

//
//@ExperimentalPagerApi
//@Preview
//@Composable
//fun PreviewTopicPagerScreen() {
//    val pagerState = rememberPagerState()
//    TopickerTheme {
//        TopicPagerScreen(
//            pagerState = pagerState,
//            viewModel = viewModel()
//        )
//    }
//}
