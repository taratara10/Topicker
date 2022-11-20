package com.kabos.topicker.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kabos.domain.navigation.TopickerNavigationDestination
import com.kabos.topicker.feature.setting.SettingRoute

object SettingDestination: TopickerNavigationDestination {
    override val route = "setting_route"
    override val destination = "setting_destination"
}

fun NavGraphBuilder.settingGraph(
    popBack: () -> Unit,
    versionName: String
) {
    composable(route = SettingDestination.route) {
        SettingRoute(
            popBack = { popBack() },
            versionName = versionName,
        )
    }
}
