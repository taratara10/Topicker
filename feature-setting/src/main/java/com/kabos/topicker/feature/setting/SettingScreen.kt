package com.kabos.topicker.feature.setting

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    versionName: String,
) {
    SettingScreen(
        popBack = { popBack() },
        versionName = versionName
    )
}

@Composable
internal fun SettingScreen(
    popBack: () -> Unit,
    versionName: String,
) {
    val context = LocalContext.current
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
                body = versionName
            )
            SettingItem(
                title = "Google Playページ",
                body = "もし気に入ったら評価をお願いします！"
            ) {
                launchGooglePlay(context)
            }
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
    onClick: () -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
        )
        Text(
            text = body,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Light,
        )
    }
}

private fun launchGooglePlay(context: Context) {
    val packageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
    TopickerTheme() {
        SettingScreen(
            popBack = {},
            versionName = "1.0.0"
        )
    }
}
