package com.kabos.topicker.ui.topic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
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
                onClickFavorite = { viewModel.updateConversationState() })
        }
    }
}
