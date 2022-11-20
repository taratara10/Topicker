package com.kabos.topicker.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabos.topicker.core.design.component.TopicAppBar
import com.kabos.topicker.core.design.theme.TopickerTheme

@Composable
fun SettingRoute(
    popBack: () -> Unit,
) {
    SettingScreen (
        popBack = { popBack() }
    )
}

@Composable
internal fun SettingScreen(
    popBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopicAppBar(
                title = "設定",
                popBack = { popBack() }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {

            SettingHeader(title = "詳細情報")
            SettingItem(
                title = "アプリのバージョン",
                body = "TODO"
            )
            Divider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun SettingHeader(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
    )
}

@Composable
fun SettingItem(
    title: String,
    body: String,
) {
    Spacer(Modifier.height(16.dp))
    Text(
        text = title,
    )
    Text(
        text = body,
        fontWeight = FontWeight.Light,
    )
}

@Preview
@Composable
fun PreviewSettingScreen() {
    TopickerTheme() {
        SettingScreen(
            popBack = {},
        )
    }
}
