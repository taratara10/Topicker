package com.kabos.topicker.feature.topic.collection

import androidx.compose.ui.graphics.Color

/**
 * pagerの各pageの情報をまとめたクラス
 * @param isDisplaying 現在のpageが表示中かどうか
 * @param shouldDisplayDial pagerのanimationとDialが被ってしまうので、animation(swipe)が終わったことを通知する
 * */
data class EachPageState(
    val index: Int,
    val isDisplaying: Boolean,
    val dialColor: Color,
    val shouldDisplayDial: Boolean,
)
