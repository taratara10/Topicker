package com.kabos.topicker.core.design.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabos.topicker.core.design.R
import com.kabos.topicker.core.design.theme.TopickerTheme

@Composable
fun FavoriteButton(
    isFavorite: Boolean = false,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorite))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isFavorite,
        restartOnPlay = true,
    )
    LottieAnimation(
        composition = composition,
        progress = { if (!isFavorite) 0f else progress },
        modifier = modifier
            .height(96.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick(!isFavorite) }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteButton() {
    TopickerTheme {
        FavoriteButton(onClick = {}, isFavorite = false)
    }
}

