package com.kabos.topicker.core.design

import com.kabos.topicker.core.design.theme.*
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DesignTest {

    @Test
    fun `最後尾のBrownから先頭のRedをまたがる要素を取得`() {
        val expected = listOf(
            Cyan300,
            LightGreen300,
            Amber300,
            Brown300,
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
        )
        assertEquals(expected, generatePagerColors(19, 15))
    }

    @Test
    fun `1周して重複する色を取得`() {
        val expected = listOf(
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
            Red300,
        )
        assertEquals(expected, generatePagerColors(20, 0))
    }
}
