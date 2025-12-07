package org.achacha.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CharGridTest {
    @Test
    fun testAccess() {
        val ary = arrayOf(
            "01234".toCharArray(),
            "abcde".toCharArray(),
            "ABCDEF".toCharArray()
        )
        val grid = CharGrid(ary)

        assertEquals('a', grid.getAt(0, 1, '_'))
        assertEquals('0', grid.getAt(0, 0, '_'))
        assertEquals('E', grid.getAt(4, 2, '_'))
        assertEquals('_', grid.getAt(0, 3, '_'))
        assertEquals('_', grid.getAt(-1, 1, '_'))
        assertEquals('_', grid.getAt(-1, -1, '_'))

    }
}