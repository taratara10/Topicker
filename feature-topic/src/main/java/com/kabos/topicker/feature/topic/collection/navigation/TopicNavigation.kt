package com.kabos.topicker.feature.topic.collection.navigation

import android.graphics.Color
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kabos.domain.navigation.TopickerNavigationDestination
import com.kabos.topicker.feature.topic.collection.TopicRoute

object TopicNavigation: TopickerNavigationDestination {
    override val route = "topic_route"
    override val destination = "topic_destination"
}

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
fun NavGraphBuilder.topicGraph(
    navigateToCollection: () -> Unit,
    setStatusBarColor: (Int) -> Unit,
) {
    composable(route = TopicNavigation.route) {
        setStatusBarColor(Color.TRANSPARENT)
        TopicRoute(
            navigateToCollection = navigateToCollection
        )
    }
}
