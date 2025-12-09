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
        assertEquals('0', grid.getAt(CharGrid.Point(0, 0), '_'))
        assertEquals('E', grid.getAt(4, 2, '_'))
        assertEquals('_', grid.getAt(0, 3, '_'))
        assertEquals('_', grid.getAt(-1, 1, '_'))
        assertEquals('_', grid.getAt(CharGrid.Point(-1, -1), '_'))
    }

    @Test
    fun findCharInGrid() {
        val grid = CharGrid(4, 4)
        grid.setAt(2, 2, '@')
        grid.setAt(0, 3, '$')
        grid.setAt(3, 1, '#')

        var p = grid.findFirst('$')
        assertNotNull(p)
        assertEquals(0, p?.x)
        assertEquals(3, p?.y)

        p = grid.findFirst('#')
        assertNotNull(p)
        assertEquals(3, p?.x)
        assertEquals(1, p?.y)

        p = grid.findFirst('a')
        assertNull(p)
    }

    @Test
    fun findAll() {
        val grid = CharGrid(4, 4)
        grid.setAt(2, 2, '@')
        grid.setAt(0, 3, '@')
        grid.setAt(3, 1, '@')

        assertEquals(3, grid.findAll('@').size)
    }

    @Test
    fun testInRange() {
        val grid = CharGrid(4, 4)

        assertTrue(grid.isInGrid(CharGrid.Point(1, 1)))
        assertTrue(grid.isInGrid(CharGrid.Point(0, 0)))
        assertTrue(grid.isInGrid(CharGrid.Point(3, 3)))
        assertFalse(grid.isInGrid(CharGrid.Point(4, 0)))
        assertFalse(grid.isInGrid(CharGrid.Point(0, 4)))
        assertFalse(grid.isInGrid(CharGrid.Point(-1, 0)))
        assertFalse(grid.isInGrid(CharGrid.Point(0, -1)))

        assertFalse(grid.isNotInGrid(CharGrid.Point(1, 1)))
        assertFalse(grid.isNotInGrid(CharGrid.Point(0, 0)))
        assertFalse(grid.isNotInGrid(CharGrid.Point(3, 3)))
        assertTrue(grid.isNotInGrid(CharGrid.Point(4, 0)))
        assertTrue(grid.isNotInGrid(CharGrid.Point(0, 4)))
        assertTrue(grid.isNotInGrid(CharGrid.Point(-1, 0)))
        assertTrue(grid.isNotInGrid(CharGrid.Point(0, -1)))
    }

}