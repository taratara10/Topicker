package com.kabos.domain.navigation

interface TopickerNavigationDestination {
    /**
     * composableへのパスを一意に表す値
     */
    val route: String

    /**
     * ネストしたnavGraphを利用するときに必要な、一意のdestination id
     */
    val destination: String
}
