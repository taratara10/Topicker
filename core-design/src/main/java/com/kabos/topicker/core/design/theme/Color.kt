package com.kabos.topicker.core.design.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Red300 = Color(0XFFE57373)
val Pink300 = Color(0XFFF06292)
val Purple300 = Color(0XFFBA68C8)
val DeepPurple300 = Color(0XFF9575CD)
val Indigo300 = Color(0XFF7986CB)
val Blue300 = Color(0XFF64B5F6)
val LightBlue300 = Color(0xFF4FC3F7)
val Cyan300 = Color(0XFF4DD0E1)
val Teal300 = Color(0XFF4DB6AC)
val Green300 = Color(0XFF81C784)
val LightGreen300 = Color(0XFFAED581)
val Lime300 = Color(0XFFDCE775)
val Yellow300 = Color(0XFFFFF176)
val Amber300 = Color(0XFFFFD54F)
val Orange300 = Color(0XFFFFB74D)
val DeepOrange300 = Color(0XFFFF8A65)
val Brown300 = Color(0XFFA1887F)
val Gray300 = Color(0XFFE0E0E0)
val BlueGray200 = Color(0XFFB0BEC5)

/** 色調をなじませるため、2つ飛ばし */
val colors = listOf(
    Red300,
    DeepPurple300,
    LightBlue300,
    Green300,
    Yellow300,
    DeepOrange300,
    BlueGray200,
    Purple300,
    Blue300,
    Teal300,
    Lime300,
    Orange300,
    Gray300,
    Pink300,
    Indigo300,
    Cyan300,
    LightGreen300,
    Amber300,
    Brown300,
)

/**
 * [colors]の順番を維持した色の配列を返す
 * @param size 戻り値の配列の要素数
 * @param startIndex [colors]で取り出していく最初のランダムな値(2つめのpagerのidを入れる運用にしてる)
 * */
fun generatePagerColors(size: Int, startIndex: Int): List<Color> {
    val colorSetSize = colors.size

    val result = mutableListOf<Color>()
    for (i in 0 until size) {
        val target = (startIndex + i) % colorSetSize
        result += colors[target]
    }
    return result
}
