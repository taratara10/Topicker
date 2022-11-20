package com.kabos.topicker.feature.setting

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kabos.topicker.core.design.component.TopicAppBar

@Composable
fun SettingRoute(
    popBack: () -> Unit,
) {

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
        Surface(modifier = Modifier.padding(innerPadding)) {
            
        }

    }
}


@Preview
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        popBack = {},
    )
}
