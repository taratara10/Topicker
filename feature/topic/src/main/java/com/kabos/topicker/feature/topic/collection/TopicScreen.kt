package com.kabos.topicker.feature.topic.collection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.core.design.component.FavoriteButton
import com.kabos.topicker.core.design.component.TopicAppBar
import com.kabos.topicker.core.design.theme.*
import com.kabos.topicker.core.model.OwnTopic
import timber.log.Timber

@ExperimentalPagerApi
@Composable
fun TopicRoute(
    modifier: Modifier = Modifier,
    viewModel: TopicViewModel = hiltViewModel(),
    navigateToCollection: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val uiState by viewModel.topicUiState.collectAsState()

    TopicScreen(
        pagerState = pagerState,
        topics = uiState.screenTopics,
        onLastPage = { viewModel.addTopic() },
        onClickFavorite = { id, isFavorite ->
            viewModel.updateConversationState(id, isFavorite)
        }
    )

}

@ExperimentalPagerApi
@Composable
fun TopicScreen(
    pagerState: PagerState,
    topics: List<OwnTopic>,
    onLastPage: () -> Unit,
    onClickFavorite:(Int, Boolean) -> Unit,
) {
    SideEffect {
        Timber.d("--ss TopicPagerScreen Recomposition")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopicAppBar(
            modifier = Modifier
                .wrapContentSize()
                .statusBarsPadding()
        )
        TopicPager(
            pagerState = pagerState,
            pageCount = topics.size,
            modifier = Modifier.fillMaxSize(),
            pagerColors = topics.map { toColor(it.topicId) },
            onLastPage = { onLastPage() }
        ) { page ->
            TopicContent(
                ownTopic = topics[page],
                isPageDisplaying = (pagerState.currentPage == page),
                onClickFavorite = { id, isFavorite ->
                    onClickFavorite(id, isFavorite)
                })
        }
    }
}

// TODO 仮置き
private fun toColor(id: Int): Color {
    return when (id % 10) {
        1 -> LightBlue100
        2 -> Blue100
        3 -> Indigo100
        4 -> DeepOrange100
        5 -> DeepPurple100
        6 -> Pink100
        7 -> Red100
        8 -> Cyan100
        9 -> Green100
        else -> Color.White
    }
}

@ExperimentalPagerApi
@Composable
fun TopicContent(
    ownTopic: OwnTopic,
    isPageDisplaying: Boolean,
    onClickFavorite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isDisplayed: Boolean by remember {
            mutableStateOf(false)
        }

        // ページが表示されたら、カードのアニメーションを発火させる
        if (isPageDisplaying) {
            isDisplayed = true
        }

        // animateOffsetAsStateもあるよ！
        val positionX by animateDpAsState(
            targetValue = if (isDisplayed) 0.dp else 50.dp,
            animationSpec = tween(1000)
        )
        val positionY by animateDpAsState(
            targetValue = if (isDisplayed) 0.dp else 400.dp,
            animationSpec = tween(1000)
        )
        val rotate by animateFloatAsState(
            targetValue = if (isDisplayed) 0f else 20f,
            animationSpec = tween(1000)
        )

        Spacer(modifier = Modifier.height(120.dp))
        TopicCard(
            text = ownTopic.title,
            positionX = positionX,
            positionY = positionY,
            rotate = rotate
        )
        Spacer(modifier = Modifier.height(30.dp))
        FavoriteButton(
            isFavorite = ownTopic.isFavorite,
            onClick = { isFavorite -> onClickFavorite(ownTopic.topicId, isFavorite) },
        )
    }
}

@Composable
fun TopicCard(
    text: String,
    positionX: Dp = 0.dp,
    positionY: Dp = 0.dp,
    rotate: Float = 0f
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .offset(x = -positionX, y = -positionY)
            .rotate(rotate)
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
@Preview(showBackground = true)
@Composable
fun PreviewTopicContent() {
    TopickerTheme {
        val sample = OwnTopic(
            topicId = 1,
            title = "〇〇な話",
            isFavorite = false,
        )
        TopicContent(
            ownTopic = sample,
            isPageDisplaying = true,
            onClickFavorite = { _, _ -> }
        )
    }
}