package com.kabos.topicker.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
            Button(onClick = { onClickSkip() }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "スキップ")
                Text(text = "スキップ")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = { onClickConversation() }) {
                Icon(Icons.Default.ThumbUp, contentDescription = "いいね")
                Text(text = "会話した")
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
