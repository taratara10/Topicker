package com.kabos.topicker.ui.collection

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kabos.domain.navigation.TopickerNavigationDestination

object CollectionDestination: TopickerNavigationDestination {
    override val route = "collection_route"
    override val destination = "collection_destination"
}

fun NavGraphBuilder.collectionGraph() {
    composable(route = CollectionDestination.route) {
        CollectionRoute()
    }
}
