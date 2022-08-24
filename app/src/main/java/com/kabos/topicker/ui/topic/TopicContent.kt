package com.kabos.topicker.ui.topic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.kabos.topicker.model.domain.ConversationState
import com.kabos.topicker.model.domain.TopicUiState
import com.kabos.topicker.ui.theme.TopickerTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.kabos.topicker.ui.theme.BlueGray100
import com.kabos.topicker.ui.theme.Yellow100

@Composable
fun TopicContent(
    uiState: TopicUiState,
    onClickConversation: () -> Unit,
    onClickSkip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        TopicCard(text = uiState.title)
        Spacer(modifier = Modifier.height(30.dp))
        Row() {
            ScalableButton(
                onClick = { onClickSkip() },
                text = "スキップ",
                activeColor = Color(0xFFFFD600),
                activeTextColor = Color.Black
            )
            Spacer(modifier = Modifier.width(32.dp))

            ScalableButton(
                onClick = { onClickConversation() },
                text = "会話した",
            )
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

@Composable
fun HeartAnimation(
    enabledColor: Color = Color.Red,
    disabledColor: Color = Color.LightGray,
    modifier: Modifier = Modifier,
) {

    val interactionSource = MutableInteractionSource()
    val coroutineScope = rememberCoroutineScope()

    var enabled by remember {
        mutableStateOf(false)
    }

    val scale = remember {
        Animatable(1f)
    }

    Icon(
        imageVector = Icons.Outlined.Favorite,
        contentDescription = "Like the product",
        tint = if (enabled) enabledColor else disabledColor,
        modifier = modifier
            .scale(scale = scale.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                enabled = !enabled
                coroutineScope.launch {
                    scale.animateTo(
                        0.8f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                }
            }
    )
}

@Composable
fun ScalableButton(
    onClick: () -> Unit,
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
    var enabled by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .scale(scale = scale.value)
            .background(
                color = if (enabled) activeColor else Color.LightGray,
                shape = RoundedCornerShape(size = 12f)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                onClick()
                enabled = !enabled
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
            color = if (enabled) activeTextColor else Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButton() {
    TopickerTheme {
//        ScalableButton("Button")
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
            title = "〇〇な話",
            color = Color.LightGray,
            conversationState = ConversationState.UnSelected
        )
        TopicContent(
            uiState = state,
            onClickConversation = { /*TODO*/ },
            onClickSkip = { /*TODO*/ })
    }
}
