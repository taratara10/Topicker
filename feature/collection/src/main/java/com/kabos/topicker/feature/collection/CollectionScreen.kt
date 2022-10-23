package com.kabos.topicker.feature.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabos.topicker.core.design.R
import com.kabos.topicker.core.design.theme.TopickerTheme
import com.kabos.topicker.core.model.OwnTopic
import timber.log.Timber

@Composable
fun CollectionRoute(
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectionUiState.collectAsState()
    CollectionScreen(
        topics = uiState.ownTopics
    )
}

@Composable
internal fun CollectionScreen(
    topics: List<OwnTopic>,
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scrollableState
    ) {
        items(topics) { topic ->
            CollectionCard(
                text = topic.title,
                isFavorite = topic.isFavorite,
                onClick = {
                    Timber.d("topicId = ${topic.topicId}")
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCollectionScreen() {
    TopickerTheme {
        val sample = listOf(
            OwnTopic(1,"sample 1", false),
            OwnTopic(2,"sample 2", true)
        )
        CollectionScreen(topics = sample)
    }
}


@Composable
private fun CollectionCard(
    text: String,
    isFavorite: Boolean,
    onClick:() -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 8.dp)
                    .padding(vertical = 8.dp),
            )
            FavoriteIcon(
                isFavorite = isFavorite,
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun FavoriteIcon(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorite))
    LottieAnimation(
        composition = composition,
        progress = { if (isFavorite) 1f else 0f },
        modifier = modifier
            .height(32.dp)
            .width(32.dp)
            .wrapContentWidth(Alignment.End)
    )
}

@Preview
@Composable
private fun PreviewCollectionCard() {
    TopickerTheme {
        CollectionCard(text = "test test", isFavorite = true, onClick = {})
    }
}
