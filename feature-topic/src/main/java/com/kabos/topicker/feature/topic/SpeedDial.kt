package com.kabos.topicker.feature.topic

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kabos.topicker.core.design.theme.BlueGray200
import kotlin.math.cos
import kotlin.math.sin

/**
 * アニメーション付きのイカしたspeedDial
 * @param toggleDial trueでdial展開、falseで閉じる
 * @param onClickSpeedDial +ボタンの親Dial
 * @param onClickLeft 左の子Dial
 * @param onClickLeft 真上の子Dial
 * @param onClickLeft 右の子Dial
 * @param distance 展開時に広がる円の半径
 * */
@Composable
fun SpeedDial(
    toggleDial: Boolean,
    onClickSpeedDial: () -> Unit,
    onClickLeft: () -> Unit,
    onClickCenter: () -> Unit,
    onClickRight: () -> Unit,
    color: Color,
    distance: Int = 120,
    height: Dp = 350.dp,
    modifier: Modifier = Modifier,
) {

    val leftRadius = 210
    val leftX by animateOuterCirclePositionXAsState(
        isDisplayed = toggleDial,
        radius = leftRadius,
        distance = distance
    )
    val leftY by animateOuterCirclePositionYAsState(
        isDisplayed = toggleDial,
        radius = leftRadius,
        distance = distance
    )

    val centerRadius = 270
    val centerX by animateOuterCirclePositionXAsState(
        isDisplayed = toggleDial,
        radius = centerRadius,
        distance = distance
    )
    val centerY by animateOuterCirclePositionYAsState(
        isDisplayed = toggleDial,
        radius = centerRadius,
        distance = distance
    )

    val rightRadius = 330
    val rightX by animateOuterCirclePositionXAsState(
        isDisplayed = toggleDial,
        radius = rightRadius,
        distance = distance
    )
    val rightY by animateOuterCirclePositionYAsState(
        isDisplayed = toggleDial,
        radius = rightRadius,
        distance = distance
    )

    val backCircleSize by animateDpAsState(
        targetValue = if (toggleDial) (distance * 2).dp else 0.dp,
        tween(500)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        ParentDial(
            onClick = { onClickSpeedDial() },
            color = color,
            toggleDial = toggleDial,
        )

        SubDial(
            onClick = { onClickLeft() },
            color = color,
            vector = Icons.Default.Menu,
            modifier = Modifier
                .offset(x = leftX, y = leftY)
                .zIndex(-1f)
        )

//        SubDial(
//            onClick = { onClickCenter() },
//            color = color,
//            vector = Icons.Default.Lock,
//            modifier = Modifier
//                .offset(x = centerX, y = centerY)
//                .zIndex(-1f)
//        )

        SubDial(
            onClick = { onClickRight() },
            color = color,
            vector = Icons.Default.Settings,
            modifier = Modifier
                .offset(x = rightX, y = rightY)
                .zIndex(-1f)
        )

        // back circle
        Box(
            modifier = Modifier
                .size(backCircleSize)
                .clip(CircleShape)
                .border(width = 2.dp, color = BlueGray200, shape = CircleShape)
                .zIndex(-10f)
        )
    }
}

@Composable
fun ParentDial(
    onClick: () -> Unit,
    color: Color,
    toggleDial: Boolean,
    size: Dp = 96.dp,
    vector: ImageVector = Icons.Default.Add,
    modifier: Modifier = Modifier,
) {
    val rotateIcon by animateFloatAsState(
        targetValue = if (toggleDial) 45f else 0f,
        animationSpec = tween(400)
    )

    Box(modifier = modifier.rotate(rotateIcon)) {
        OutlinedButton(
            onClick = { onClick() },
            modifier = modifier.size(size),  //avoid the oval shape
            shape = CircleShape,
            border = BorderStroke(0.dp, Color.Transparent),
            contentPadding = PaddingValues(0.dp),  //avoid the little icon
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = color,
                contentColor = Color.Black,
            ),
        ) {
            Icon(vector, contentDescription = "",)
        }
    }

}

@Composable
fun SubDial(
    onClick: () -> Unit,
    size: Dp = 72.dp,
    color: Color,
    vector: ImageVector,
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
        Icon(imageVector = vector, contentDescription = "")
    }
}

/**
 * 外円上のx座標をθからアニメーション付きで算出する
 * easingは↓のサイトで自作した
 * https://cubic-bezier.com/#.27,1.16,.29,1.06
 *
 * @param radius 0~360の表示する位置の角度
 * @param distance 展開後の移動距離
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
        animationSpec = tween(1000, easing = CubicBezierEasing(.27f, 1.6f, .29f, 1f))
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
        animationSpec = tween(1000, easing = CubicBezierEasing(.27f, 1.6f, .29f, 1f))
    )
}

@Preview
@Composable
fun PreviewLiquidDial() {
    var isDisplay by remember {
        mutableStateOf(false)
    }
    SpeedDial(
        toggleDial = isDisplay,
        onClickSpeedDial = { isDisplay = !isDisplay},
        onClickLeft = {},
        onClickCenter = {},
        onClickRight = {},
        color = Color.Green,
    )
}
