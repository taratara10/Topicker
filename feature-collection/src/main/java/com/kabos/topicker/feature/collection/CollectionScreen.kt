package com.kabos.topicker.feature.collection

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabos.topicker.core.design.R
import com.kabos.topicker.core.design.component.BubbleLoading
import com.kabos.topicker.core.design.component.FavoriteButton
import com.kabos.topicker.core.design.component.TopicAppBar
import com.kabos.topicker.core.design.theme.Lime100
import com.kabos.topicker.core.design.theme.TopickerTheme
import com.kabos.topicker.core.model.OwnTopic
import com.kabos.topicker.core.model.previewOwnTopics

@ExperimentalLifecycleComposeApi
@Composable
fun CollectionRoute(
    popBack: () -> Unit,
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectionUiState.collectAsStateWithLifecycle()
    CollectionScreen(
        uiState = uiState,
        popBack = { popBack() },
        onClickFavorite = { id, isFavorite -> viewModel.updateFavoriteState(id, isFavorite) },
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun CollectionScreen(
    uiState: CollectionUiState,
    popBack: () -> Unit,
    onClickFavorite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberLazyListState()
    var showDialog by remember {
        mutableStateOf(false)
    }
    var clickedTopicId by remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopicAppBar(
                title = "集めた話題",
                popBack = { popBack() }
            )
        },
    ) { _ ->

        when (uiState) {
            is CollectionUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Lime100)
                        .navigationBarsPadding()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = scrollableState
                ) {
                    items(uiState.ownTopics) { topic ->
                        CollectionCard(
                            text = topic.title,
                            isFavorite = topic.isFavorite,
                            onClick = {
                                clickedTopicId = topic.topicId
                                showDialog = true
                            },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                if (showDialog) {
                    uiState.ownTopics.find { it.topicId == clickedTopicId }?.let { selectedTopic ->
                        CardDialog(
                            ownTopic = selectedTopic,
                            onClickFavorite = { isFavorite ->
                                onClickFavorite(selectedTopic.topicId, isFavorite)
                            },
                            onDismiss = { showDialog = false }
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


}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    showSystemUi = true,
)
@Composable
private fun PreviewCollectionScreen() {
    TopickerTheme(darkTheme = false) {
        CollectionScreen(
            uiState = CollectionUiState.Success(previewOwnTopics),
            popBack = {},
            onClickFavorite = { _, _ -> }
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
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.Start)
                    .padding(16.dp)
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

@Composable
fun CardDialog(
    ownTopic: OwnTopic,
    onClickFavorite: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = ownTopic.title,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp)
                )
                FavoriteButton(
                    isFavorite = ownTopic.isFavorite,
                    onClick = { isFavorite -> onClickFavorite(isFavorite) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCollectionCard() {
    TopickerTheme {
        CollectionCard(text = "test test", isFavorite = true, onClick = {})
    }
}

@Preview
@Composable
private fun PreviewCardDialog() {
    TopickerTheme {
        CardDialog(
            ownTopic = previewOwnTopics.first(),
            onClickFavorite = { _ -> },
            onDismiss = {},
        )
    }
}
