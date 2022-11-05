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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kabos.topicker.core.design.component.BubbleLoading
import com.kabos.topicker.core.design.component.FavoriteButton
import com.kabos.topicker.core.design.theme.TopickerTheme
import com.kabos.topicker.core.design.theme.generatePagerColors
import com.kabos.topicker.core.model.OwnTopic
import kotlinx.coroutines.delay

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
@Composable
fun TopicRoute(
    modifier: Modifier = Modifier,
    viewModel: TopicViewModel = hiltViewModel(),
    navigateToCollection: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val uiState by viewModel.topicUiState.collectAsStateWithLifecycle()

    TopicScreen(
        pagerState = pagerState,
        topicUiState = uiState,
        onLastPage = { viewModel.addTopic() },
        onClickFavorite = { id, isFavorite ->
            viewModel.updateFavoriteState(id, isFavorite)
        },
        onClickCollection = { navigateToCollection() },
        registerOwnTopic = { id -> viewModel.registerOwnTopic(id) }
    )
}

@ExperimentalPagerApi
@Composable
fun TopicScreen(
    pagerState: PagerState,
    topicUiState: TopicUiState,
    onLastPage: () -> Unit,
    onClickFavorite: (Int, Boolean) -> Unit,
    onClickCollection: () -> Unit,
    registerOwnTopic: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (topicUiState) {
        is TopicUiState.Success -> {
            val topics = topicUiState.screenTopics
            TopicPager(
                pagerState = pagerState,
                pageCount = topics.size,
                pagerColors = generatePagerColors(topics.size, topics[1].topicId),
                onLastPage = { onLastPage() },
                modifier = Modifier.fillMaxSize(),
            ) { eachPageState ->
                if (eachPageState.index == 0) {
                    TutorialContent(
                        ownTopic = topics.first()
                    )
                } else {
                    TopicContent(
                        ownTopic = topics[eachPageState.index],
                        isCurrentPageDisplaying = eachPageState.isDisplaying,
                        shouldDisplayDial = eachPageState.shouldDisplayDial,
                        dialColor = eachPageState.dialColor,
                        onClickFavorite = { id, isFavorite ->
                            onClickFavorite(id, isFavorite)
                        },
                        onClickCollection = { onClickCollection() },
                        registerOwnTopic = { id -> registerOwnTopic(id) }
                    )
                }
            }
        }
        else -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                BubbleLoading()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopicContent(
    ownTopic: OwnTopic,
    isCurrentPageDisplaying: Boolean,
    registerOwnTopic: (Int) -> Unit,
    onClickFavorite: (Int, Boolean) -> Unit,
    onClickCollection: () -> Unit,
    shouldDisplayDial: Boolean,
    dialColor: Color,
    modifier: Modifier = Modifier,
) {

    var startCardAnimation: Boolean by remember {
        mutableStateOf(false)
    }

    var showSpeedDial by remember {
        mutableStateOf(false)
    }

    var toggleSpeedDial by remember {
        mutableStateOf(false)
    }

    if (isCurrentPageDisplaying) {
        startCardAnimation = true
        registerOwnTopic(ownTopic.topicId)
    }

    // animateOffsetAsStateもあるよ！
    val positionX by animateDpAsState(
        targetValue = if (startCardAnimation) 0.dp else 50.dp,
        animationSpec = tween(1000)
    )
    val positionY by animateDpAsState(
        targetValue = if (startCardAnimation) 0.dp else 400.dp,
        animationSpec = tween(1000)
    )
    val rotate by animateFloatAsState(
        targetValue = if (startCardAnimation) 0f else 20f,
        animationSpec = tween(1000)
    )

    // dialがちらつくので、delayする
    if (shouldDisplayDial) {
        LaunchedEffect(Unit) {
            delay(100)
            showSpeedDial = true
        }
    } else {
        showSpeedDial = false
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        // SpeedDialを下揃えにするため、fillMaxSizeの雰囲気でSpacerをいれる
        Spacer(modifier = Modifier.weight(1f))
        // pagerのanimationと被ってしまうので、animation(swipe)が終わったら表示する
        if (showSpeedDial) {
            SpeedDial(
                toggleDial = toggleSpeedDial,
                onClickSpeedDial = { toggleSpeedDial = !toggleSpeedDial },
                onClickLeft = { onClickCollection() },
                onClickCenter = {},
                onClickRight = {},
                color = dialColor,
            )
        } else {
            toggleSpeedDial = false
        }
    }
}

@Composable
fun TutorialContent(
    modifier: Modifier = Modifier,
    ownTopic: OwnTopic,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(120.dp))
        TopicCard(text = ownTopic.title)
    }
}

@Composable
fun TopicCard(
    text: String,
    positionX: Dp = 0.dp,
    positionY: Dp = 0.dp,
    rotate: Float = 0f,
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
            maxLines = 2,
            modifier = Modifier.padding(vertical = 72.dp)
        )
    }
}

/**
 * Preview
 * */
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTopicCard() {
    TopickerTheme {
        TopicCard(text = "おもしろい話")
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTopicScreen() {
    val pagerState = rememberPagerState()
    val uiStata = TopicUiState.Loading
    val sample = OwnTopic(
        topicId = 1,
        title = "〇〇な話",
        isFavorite = false,
    )
    TopickerTheme {
        TopicScreen(
            pagerState = pagerState,
            topicUiState = uiStata,
            onLastPage = {},
            onClickFavorite = { _, _ -> },
            onClickCollection = {},
            registerOwnTopic = {}
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
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
            isCurrentPageDisplaying = true,
            shouldDisplayDial = true,
            dialColor = Color.Green,
            onClickFavorite = { _, _ -> },
            onClickCollection = {},
            registerOwnTopic = {},
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTutorialContent() {
    TopickerTheme {
        val sample = OwnTopic(
            10000,
            " Let's go! \uD83D\uDC49",
            false
        )
        TutorialContent(
            ownTopic = sample,
        )
    }
}
