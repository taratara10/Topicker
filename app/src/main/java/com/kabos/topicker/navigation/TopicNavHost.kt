package com.kabos.topicker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kabos.topicker.feature.topic.collection.CollectionDestination
import com.kabos.topicker.feature.topic.collection.collectionGraph
import com.kabos.topicker.ui.topic.TopicNavigation
import com.kabos.topicker.ui.topic.topicGraph

@ExperimentalPagerApi
@Composable
fun TopicNavHost(
    navHostController: NavHostController,
    startDestination: String = TopicNavigation.route,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        topicGraph(
            navigateToCollection = { navHostController.navigate(com.kabos.topicker.feature.topic.collection.CollectionDestination.route)}
        )
        collectionGraph()
    }
}
