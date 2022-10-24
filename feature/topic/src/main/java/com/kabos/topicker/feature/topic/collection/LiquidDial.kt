package com.kabos.topicker.feature.topic.collection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpeedDial(
    distance: Int = 118,
    modifier: Modifier = Modifier,
) {
    var isDisplayed by remember {
        mutableStateOf(false)
    }

    val leftRadius = 210
    val leftX by animateOuterCirclePositionXAsState(
        isDisplayed = isDisplayed,
        radius = leftRadius,
        distance = distance
    )
    val leftY by animateOuterCirclePositionYAsState(
        isDisplayed = isDisplayed,
        radius = leftRadius,
        distance = distance
    )

    val centerRadius = 270
    val centerX by animateOuterCirclePositionXAsState(
        isDisplayed = isDisplayed,
        radius = centerRadius,
        distance = distance
    )
    val centerY by animateOuterCirclePositionYAsState(
        isDisplayed = isDisplayed,
        radius = centerRadius,
        distance = distance
    )

    val rightRadius = 330
    val rightX by animateOuterCirclePositionXAsState(
        isDisplayed = isDisplayed,
        radius = rightRadius,
        distance = distance
    )
    val rightY by animateOuterCirclePositionYAsState(
        isDisplayed = isDisplayed,
        radius = rightRadius,
        distance = distance
    )

    val backCircleSize by animateDpAsState(
        targetValue = if (isDisplayed) 240.dp else 0.dp,
        tween(1000)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CenterDial(onClick = { isDisplayed = !isDisplayed }, color = Color.Cyan)
        SubDial(
            onClick = { /*TODO*/ },
            color = Color.Blue,
            icon = Icons.Default.Lock,
            modifier = Modifier
                .offset(x = leftX, y = leftY)
                .zIndex(-1f)
        )
        SubDial(
            onClick = { /*TODO*/ },
            color = Color.Blue,
            icon = Icons.Default.Lock,
            modifier = Modifier
                .offset(x = centerX, y = centerY)
                .zIndex(-1f)
        )
        SubDial(
            onClick = { /*TODO*/ },
            color = Color.Blue,
            icon = Icons.Default.Lock,
            modifier = Modifier
                .offset(x = rightX, y = rightY)
                .zIndex(-1f)
        )
        Box(
            modifier = Modifier
                .size(backCircleSize)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.LightGray, shape = CircleShape)
                .zIndex(-5f)
        )
    }

}

@Composable
fun CenterDial(
    onClick: () -> Unit,
    size: Dp = 96.dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier.size(size),  //avoid the oval shape
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
            contentColor = Color.Black,
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "")
    }
}

@Composable
fun SubDial(
    onClick: () -> Unit,
    size: Dp = 72.dp,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier.size(size),  //avoid the oval shape
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
            contentColor = Color.Black,
        )
    ) {
        Icon(imageVector = icon, contentDescription = "")
    }
}

@Preview
@Composable
fun PreviewLiquidDial() {
    SpeedDial()
}

/**
 * 外円上のx座標をθからアニメーション付きで算出する
 * */
@Composable
fun animateOuterCirclePositionXAsState(
    isDisplayed: Boolean,
    radius: Int,
    distance: Int,
): State<Dp> {
    val positionX = distance * cos(Math.toRadians(radius.toDouble()))
    return animateDpAsState(
        targetValue = if (isDisplayed) positionX.dp else 0.dp,
        animationSpec = tween(1000)
    )
}

/**
 * 外円上のy座標をθからアニメーション付きで算出する
 * */
@Composable
fun animateOuterCirclePositionYAsState(
    isDisplayed: Boolean,
    radius: Int,
    distance: Int,
): State<Dp> {
    val positionX = distance * sin(Math.toRadians(radius.toDouble()))
    return animateDpAsState(
        targetValue = if (isDisplayed) positionX.dp else 0.dp,
        animationSpec = tween(1000)
    )
}
