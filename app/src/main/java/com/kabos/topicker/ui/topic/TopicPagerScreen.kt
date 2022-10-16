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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.model.domain.TopicUiState
import timber.log.Timber

@ExperimentalPagerApi
@Composable
fun TopicRoute(
    modifier: Modifier = Modifier,
    viewModel: TopicViewModel = hiltViewModel(),
    navigateToCollection: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val topics by viewModel.topicUiState.collectAsState()

    TopicPagerScreen(
        pagerState = pagerState,
        topics = topics,
        onLastPage = { viewModel.addTopic() },
        onClickFavorite = { id, isFavorite ->
            viewModel.updateConversationState(id, isFavorite)
        }
    )

}

@ExperimentalPagerApi
@Composable
fun TopicPagerScreen(
    pagerState: PagerState,
    topics: List<TopicUiState>,
    onLastPage: () -> Unit,
    onClickFavorite:(Int, Boolean) -> Unit,
) {
    SideEffect {
        Timber.d("--ss TopicPagerScreen Recomposition")
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
            pagerColors = topics.map { it.color },
            onLastPage = { onLastPage() }
        ) { page ->
            TopicContent(
                uiState = topics[page],
                isPageDisplaying = (pagerState.currentPage == page),
                onClickFavorite = { id, isFavorite ->
                    onClickFavorite(id, isFavorite)
                })
        }
    }
}
