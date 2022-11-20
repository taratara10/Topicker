package com.kabos.topicker.feature.topic

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
import com.kabos.topicker.core.design.theme.generatePagerColors
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.previewOwnTopics
import kotlinx.coroutines.delay

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
@Composable
fun TopicRoute(
    viewModel: TopicViewModel = hiltViewModel(),
    navigateToCollection: () -> Unit,
    navigateToSetting: () -> Unit,
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
        onClickSetting = { navigateToSetting() },
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
    onClickSetting: () -> Unit,
    registerOwnTopic: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (topicUiState) {
        is TopicUiState.Success -> {
            val topics = topicUiState.screenTopics
            /* pager */
            TopicPager(
                pagerState = pagerState,
                pageCount = topics.size,
                pagerColors = generatePagerColors(topics.size, topics[1].topicId),
                onLastPage = { onLastPage() },
                modifier = modifier.fillMaxSize(),
            ) { eachPageState ->
                if (eachPageState.index == 0) {
                    TutorialContent(
                        ownTopic = topics.first(),
                        shouldDisplayDial = eachPageState.shouldDisplayDial,
                        onClickCollection = { onClickCollection() },
                        onClickSetting = { onClickSetting() },
                        dialColor = eachPageState.dialColor,
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
                        onClickSetting = { onClickSetting() },
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
    onClickSetting: () -> Unit,
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
                onClickRight = { onClickSetting() },
                color = dialColor,
            )
        } else {
            toggleSpeedDial = false
        }
    }
}


/**
 * 1ページ目のtutorialでは、[SpeedDial]の機能を伝えるために展開しておく
 * */
@Composable
fun TutorialContent(
    modifier: Modifier = Modifier,
    ownTopic: OwnTopic,
    onClickCollection: () -> Unit,
    onClickSetting: () -> Unit,
    shouldDisplayDial: Boolean,
    dialColor: Color,
) {
    var showSpeedDial by remember {
        mutableStateOf(false)
    }

    var toggleSpeedDial by remember {
        mutableStateOf(false)
    }
    // dialがちらつくので、delayする
    if (shouldDisplayDial) {
        LaunchedEffect(Unit) {
            showSpeedDial = true
            delay(500)
            toggleSpeedDial = true
        }
    } else {
        showSpeedDial = false
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        TopicCard(text = ownTopic.title)
        Spacer(modifier = Modifier.weight(1f))
        // pagerのanimationと被ってしまうので、animation(swipe)が終わったら表示する
        if (showSpeedDial) {
            SpeedDial(
                toggleDial = toggleSpeedDial,
                onClickSpeedDial = { toggleSpeedDial = !toggleSpeedDial },
                onClickLeft = { onClickCollection() },
                onClickCenter = {},
                onClickRight = { onClickSetting() },
                color = dialColor,
            )
        } else {
            toggleSpeedDial = false
        }
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
            modifier = Modifier.padding(vertical = 72.dp, horizontal = 16.dp)
        )
    }
}

/**
 * Preview
 * */
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTopicCard() {
    TopicCard(text = "おもしろい話")
}

@ExperimentalPagerApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTopicContent() {
    TopicContent(
        ownTopic = previewOwnTopics.first(),
        isCurrentPageDisplaying = true,
        shouldDisplayDial = true,
        dialColor = Color.Green,
        onClickFavorite = { _, _ -> },
        onClickCollection = {},
        onClickSetting ={} ,
        registerOwnTopic = {},
    )
}

@ExperimentalPagerApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTutorialContent() {
    val sample = OwnTopic(
        9999,
        "各トピックについて自由に雑談しましょう！\n\uD83D\uDC49",
        false
    )
    TutorialContent(
        ownTopic = sample,
        dialColor = Color.Cyan,
        onClickCollection = {},
        onClickSetting = {},
        shouldDisplayDial = true
    )
}
