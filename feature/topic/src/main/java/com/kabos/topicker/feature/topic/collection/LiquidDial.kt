package com.kabos.topicker.feature.topic.collection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
    distance: Int = 96,
    modifier: Modifier = Modifier,
) {
    var isDisplayed by remember {
        mutableStateOf(false)
    }
    val positionX by animateDpAsState(
        targetValue = if (isDisplayed) 0.dp else calcCircumferentialCoordinates(45, distance).first.dp,
        animationSpec = tween(1000)
    )
    val positionY by animateDpAsState(
        targetValue = if (isDisplayed) 0.dp else calcCircumferentialCoordinates(135, distance).first.dp,
        animationSpec = tween(1000)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(300.dp)
    ) {
        CenterDial(onClick = { isDisplayed = !isDisplayed}, color = Color.Cyan)
        SubDial(
            onClick = { /*TODO*/ },
            color = Color.Blue,
            icon = Icons.Default.Lock,
            modifier = Modifier
                .offset(x = positionX, y = positionY)
                .zIndex(-1f)
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
        modifier= modifier.size(size),  //avoid the oval shape
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
            contentColor =  Color.Black,
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "content description")
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
        modifier= modifier.size(size),  //avoid the oval shape
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
            contentColor =  Color.Black,
        )
    ) {
        Icon(icon, contentDescription = "content description")
    }
}

@Preview
@Composable
fun PreviewLiquidDial() {
    SpeedDial()
}

/**
 * 円周上の座標を計算する
 * @return Pair<x座標, y座標>
 * */
fun calcCircumferentialCoordinates(
    radius: Int,
    distance: Int,
): Pair<Double, Double> {
    val rad = Math.toRadians(radius.toDouble())
    val x = distance * cos(rad)
    val y = distance * sin(rad)
    return Pair(x, y)
}
