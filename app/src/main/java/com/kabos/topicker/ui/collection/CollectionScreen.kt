package com.kabos.topicker.ui.collection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabos.topicker.ui.theme.TopickerTheme

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
) {

}

@Composable
fun CollectionCard(
    text: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .padding(start = 8.dp)
                .padding(vertical = 8.dp),
        )
    }
}

@Preview
@Composable
fun PreviewCollectionCard() {
    TopickerTheme {
        CollectionCard(text = "test test", isFavorite = true)
    }
}
