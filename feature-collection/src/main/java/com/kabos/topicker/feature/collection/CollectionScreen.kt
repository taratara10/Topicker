package com.kabos.topicker.feature.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabos.topicker.core.design.R
import com.kabos.topicker.core.design.component.TopicAppBar
import com.kabos.topicker.core.design.theme.Lime100
import com.kabos.topicker.core.design.theme.TopickerTheme
import com.kabos.topicker.core.model.OwnTopic
import timber.log.Timber

@ExperimentalLifecycleComposeApi
@Composable
fun CollectionRoute(
    popBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectionUiState.collectAsStateWithLifecycle()
    CollectionScreen(
        topics = uiState.ownTopics,
        popBack = { popBack() }
    )
}

@Composable
internal fun CollectionScreen(
    topics: List<OwnTopic>,
    popBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberLazyListState()

    Column(modifier = modifier.background(Lime100)) {
        TopicAppBar(
            title = "集めた話題",
            popBack = { popBack() }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 16.dp),
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
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun PreviewCollectionScreen() {
    val sample = listOf(
        OwnTopic(1, "sample 1", false),
        OwnTopic(2, "sample 2", true)
    )
    TopickerTheme(darkTheme = false) {
        CollectionScreen(
            topics = sample,
            popBack = {}
        )
    }
}


@Composable
private fun CollectionCard(
    text: String,
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .padding(vertical = 16.dp),
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
            .height(48.dp)
            .width(48.dp)
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
