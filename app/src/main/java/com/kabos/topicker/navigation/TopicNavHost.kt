package com.kabos.topicker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kabos.topicker.feature.collection.navigation.CollectionDestination
import com.kabos.topicker.feature.collection.navigation.collectionGraph
import com.kabos.topicker.feature.topic.collection.navigation.TopicNavigation
import com.kabos.topicker.feature.topic.collection.navigation.topicGraph

@ExperimentalLifecycleComposeApi
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
            navigateToCollection = { navHostController.navigate(CollectionDestination.route)},
        )
        collectionGraph(
            popBack = {navHostController.popBackStack()},
        )
    }
}
