@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.kabos.topicker.feature.topic

import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.google.accompanist.pager.*
import com.kabos.topicker.core.design.theme.TopickerTheme
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlin.math.absoluteValue

/**
 * スワイプでBubble animationが起こるpager
 * @param circleMinRadius 中心の小さな円の半径
 * @param circleMaxRadius 背景(実際には巨大な円)の半径
 * @param circleBottomPadding 中心の小さな円に対してのbottomPadding [SpeedDial]のheight/2にする
 * */
@ExperimentalPagerApi
@Composable
fun TopicPager(
    pagerState: PagerState,
    pageCount: Int,
    circleMinRadius: Dp = 48.dp,
    circleMaxRadius: Dp = 12000.dp,
    circleBottomPadding: Dp = 175.dp,
    pagerColors: List<Color>,
    onLastPage: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable PagerScope.(EachPageState) -> Unit
) {
    // circleWithIcon用のパラメーター
    val vector = Icons.Default.Add
    val icon = rememberVectorPainter(Icons.Default.Add)

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val circleWithIconRadius by animateDpAsState(
        targetValue = if (pagerState.shouldHideBubble(isDragged)) 0.dp else circleMinRadius,
        animationSpec = tween(350)
    )
    val circleWithIconSize by animateDpAsState(
        targetValue = if (pagerState.shouldHideBubble(isDragged)) 0.dp else vector.defaultHeight,
        animationSpec = tween(350)
    )

    Box(modifier = modifier) {
        HorizontalPager(
            count = pageCount,
            state = pagerState,
            flingBehavior = springPagerFlingBehavior(pagerState),
            modifier = Modifier.drawBehind {
                // background
                drawRect(color = pagerColors[pagerState.currentPage], size = size)
                val (radius, centerX) = calculateBackgroundCircleDimensions(
                    swipeProgress = pagerState.currentPageOffset,
                    easing = CubicBezierEasing(1f, 0f, .92f, .62f),
                    minRadius = circleMinRadius,
                    maxRadius = circleMaxRadius,
                    isLastPage = pagerState.isLastPage()
                )
                drawBackgroundCircle(
                    radius = radius,
                    centerX = centerX,
                    bottomPadding = circleBottomPadding,
                    color = pagerState.getCircleColor(pagerColors)
                )
                drawCircleWithIcon(
                    radius = circleWithIconRadius,
                    bottomPadding = circleBottomPadding,
                    color = pagerState.getNextSwipeableCircleColor(pagerColors),
                    icon = icon,
                    iconSize = circleWithIconSize
                )
            }
        ) { index ->
            content(
                EachPageState(
                    index = index,
                    isDisplaying = (pagerState.currentPage == index),
                    dialColor = pagerState.getNextSwipeableCircleColor(pagerColors),
                    shouldDisplayDial = (circleWithIconRadius == circleMinRadius)
                )
            )
            if ((index + 1) == pageCount) onLastPage()
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@Composable
fun springPagerFlingBehavior(pagerState: PagerState) =
    PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = spring(dampingRatio = 1.9f, stiffness = 600f),
    )

fun DrawScope.drawBackgroundCircle(
    radius: Dp,
    centerX: Dp,
    bottomPadding: Dp,
    color: Color
) {
    translate(left = size.width / 2) {
        drawCircle(
            color = color,
            radius = radius.toPx(),
            center = Offset(centerX.toPx(), size.height - bottomPadding.toPx())
        )
    }
}

fun DrawScope.drawCircleWithIcon(
    radius: Dp,
    bottomPadding: Dp,
    color: Color,
    icon: VectorPainter,
    iconSize: Dp
) {
    translate(left = size.width / 2) {
        drawCircle(
            radius = radius.toPx(),
            color = color,
            center = Offset(0.dp.toPx(), size.height - bottomPadding.toPx())
        )
        with(icon) {
            iconSize.toPx().let { iconSize ->
                translate(
                    top = size.height - bottomPadding.toPx() - (iconSize / 2),
                    left = -(iconSize / 2)
                ) {
                    draw(size = Size(iconSize, iconSize))
                }
            }
        }
    }
}

/**
 * 背景の円の相似変換の計算
 * 最後のページの時は円を消す
 * @param swipeProgress [PagerState.currentPageOffset]の-1f~1fの値を入れる想定
 * */
fun calculateBackgroundCircleDimensions(
    swipeProgress: Float,
    easing: Easing,
    minRadius: Dp,
    maxRadius: Dp,
    isLastPage: Boolean,
): Pair<Dp, Dp> {
    // -1f~0fの左スワイプを0f~1f, 0f~1fの右スワイプを1f~2fに変換
    val swipeValue = lerp(0f, 2f, swipeProgress.absoluteValue)
    // radiusとcenterXのmin,max値を連動させることで、円の接線を中心に固定して動かせる
    val radius = lerp(
        if (!isLastPage) minRadius else 0.dp,
        maxRadius,
        easing.transform(swipeValue)
    )
    var centerX = lerp(
        0.dp,
        maxRadius,
        easing.transform(swipeValue)
    )
    if (swipeProgress < 0) {
        centerX = -centerX
    }
    return Pair(radius, centerX)
}

@OptIn(ExperimentalPagerApi::class)
fun PagerState.shouldHideBubble(isDragged: Boolean): Boolean = derivedStateOf {
    val isDragging = (isDragged || currentPageOffset.absoluteValue > 0.1)
    isDragging || isLastPage()
}.value

@OptIn(ExperimentalPagerApi::class)
fun PagerState.getCircleColor(circleColors: List<Color>): Color {
    /** offset は-1~0が左スワイプ、0~1が右スワイプ */
    val index = if (currentPageOffset < 0) {
        currentPage - 1
    } else {
        nextSwipeablePageIndex
    }
    return circleColors[index]
}

@OptIn(ExperimentalPagerApi::class)
fun PagerState.getNextSwipeableCircleColor(circleColors: List<Color>): Color {
    return circleColors[nextSwipeablePageIndex]
}

@OptIn(ExperimentalPagerApi::class)
fun PagerState.isLastPage(): Boolean = derivedStateOf {
    ((currentPage + 1) == pageCount)
}.value

/**
 * 次のページのIndexを取得する
 * 最後のページだった場合、そのページの値を返す
 *
 * [PagerState.currentPage]が0から始まるので、+1してcurrentPageと比較する
 * */
@OptIn(ExperimentalPagerApi::class)
private val PagerState.nextSwipeablePageIndex
    get() = if (isLastPage()) currentPage - 1 else currentPage + 1

/**
 * Floatの線形補間
 * @see [lerp]
 * */
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

@ExperimentalPagerApi
@Preview
@Composable
fun PreviewTopicPager() {
    val pagerState = rememberPagerState()
    TopickerTheme() {
        val color = listOf(Color.Blue, Color.Cyan, Color.Yellow, Color.Green)
        TopicPager(
            pagerState = pagerState,
            pageCount = 10,
            modifier = Modifier.fillMaxSize(),
            pagerColors = color,
            onLastPage = {}
        ) { pagerState ->
            Text(text = "page is ${pagerState.index}", modifier = Modifier.fillMaxHeight())
        }
    }
}
