package com.kabos.topicker.ui.topic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabos.topicker.R
import com.kabos.topicker.model.domain.ConversationState
import com.kabos.topicker.model.domain.TopicUiState
import com.kabos.topicker.ui.theme.TopickerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopicContent(
    uiState: TopicUiState,
    onClickFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO viewPagerはcomposeScopeが破棄されるっぽいので、Flowで持ちたい
        // 発火した瞬間を検知して更新したい updateUIState
        var isFire by remember {
            mutableStateOf(false)
        }

        // animateOffsetAsStateもあるよ！
        val positionX by animateDpAsState(
            targetValue = if (!isFire) 50.dp else 0.dp,
            animationSpec = tween(1000)
        )
        val positionY by animateDpAsState(
            targetValue = if (!isFire) 400.dp else 0.dp,
            animationSpec = tween(1000)
        )
        val rotate by animateFloatAsState(
            targetValue = if (!isFire) 20f else 0f,
            animationSpec = tween(1000)
        )

        // TODO これだと、addですぐ発火してアニメーションが見えない
        LaunchedEffect(Unit) {
            delay(1000)
            isFire = true
        }

        Spacer(modifier = Modifier.height(120.dp))
        TopicCard(
            text = uiState.title,
            positionX = positionX,
            positionY = positionY,
            rotate = rotate
        )
        Spacer(modifier = Modifier.height(30.dp))
        FavoriteButton(onClick = { onClickFavorite()})
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

@Composable
fun FavoriteButton(
    selected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO このremember, hoisted で不要になるかも
    var isSelected: Boolean by remember {
        mutableStateOf(selected)
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorite))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isSelected,
        restartOnPlay = true,
    )

    LottieAnimation(
        composition = composition,
        progress = { if (!isSelected) 0f else progress },
        modifier = modifier
            .height(96.dp)
            .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
            isSelected = !isSelected
        }
    )
}


@Composable
fun ScalableButton(
    onClick: () -> Unit,
    isEnabled: Boolean = false,
    text: String,
    activeColor: Color = Color(0xFF35898F),
    activeTextColor: Color = Color.White,
    animationDuration: Int = 100,
    scaleDown: Float = 0.9f,
    modifier: Modifier = Modifier
) {
    val interactionSource = MutableInteractionSource()
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    Box(
        modifier = modifier
            .scale(scale = scale.value)
            .background(
                color = if (isEnabled) activeColor else Color.LightGray,
                shape = RoundedCornerShape(size = 12f)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                onClick()
                coroutineScope.launch {
                    scale.animateTo(
                        scaleDown,
                        animationSpec = tween(animationDuration),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(animationDuration),
                    )
                }
            }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            color = if (isEnabled) activeTextColor else Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButton() {
    TopickerTheme {
        ScalableButton(
            onClick = {},
            text = "Button"
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

@Preview(showBackground = true)
@Composable
fun PreviewTopicContent() {
    TopickerTheme {
        val state = TopicUiState(
            id = 1,
            title = "〇〇な話",
            color = Color.LightGray,
            conversationState = ConversationState.UnSelected
        )
        TopicContent(
            uiState = state,
            onClickFavorite = {}
        )
    }
}
