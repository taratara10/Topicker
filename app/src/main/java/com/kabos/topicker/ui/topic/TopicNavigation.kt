package com.kabos.topicker.ui.topic

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kabos.domain.navigation.TopickerNavigationDestination

object TopicNavigation: TopickerNavigationDestination {
    override val route = "topic_route"
    override val destination = "topic_destination"
}

@ExperimentalPagerApi
fun NavGraphBuilder.topicGraph(navigateToCollection: () -> Unit) {
    composable(route = TopicNavigation.route) {
        TopicRoute(
            navigateToCollection = navigateToCollection
        )
    }
}
