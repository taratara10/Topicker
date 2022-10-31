package com.kabos.topicker.feature.collection.navigation

import android.graphics.Color
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kabos.domain.navigation.TopickerNavigationDestination
import com.kabos.topicker.feature.collection.CollectionRoute

object CollectionDestination: TopickerNavigationDestination {
    override val route = "collection_route"
    override val destination = "collection_destination"
}

@ExperimentalLifecycleComposeApi
fun NavGraphBuilder.collectionGraph(
    popBack: () -> Unit,
    setStatusBarColor: (Int) -> Unit,
) {
    composable(route = CollectionDestination.route) {
        setStatusBarColor(Color.WHITE)
        CollectionRoute(popBack = { popBack() })
    }
}
