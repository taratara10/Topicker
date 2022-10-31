package com.kabos.topicker.core.design.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kabos.topicker.core.design.theme.TopickerTheme

@Composable
fun TopicAppBar(
    title: String,
    popBack:() -> Unit,
    background: Color = Color.White,
    contentColor: Color = Color.DarkGray,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = background,
        elevation = 10.dp,
        navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back",
                )
            }
        },
//        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = "Menu icon",
//                )
//            }
//        },
        contentColor = contentColor,
        modifier = modifier.statusBarsPadding()
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTopicAppBar() {
    TopickerTheme {
        TopicAppBar(title = "title", popBack = {})
    }
}
