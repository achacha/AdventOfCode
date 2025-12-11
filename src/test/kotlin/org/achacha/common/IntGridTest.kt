package org.achacha.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntGridTest {
    @Test
    fun findCharInGrid() {
        val grid = IntGrid(4, 4)
        grid.setAt(2, 2, 13)
        grid.setAt(0, 3, 9)
        grid.setAt(3, 1, 102)

        var p = grid.findFirst(9)
        assertNotNull(p)
        assertEquals(0, p?.x)
        assertEquals(3, p?.y)

        p = grid.findFirst(102)
        assertNotNull(p)
        assertEquals(3, p?.x)
        assertEquals(1, p?.y)

        p = grid.findFirst(-2)
        assertNull(p)
    }

    @Test
    fun findAll() {
        val grid = IntGrid(4, 4)
        grid.setAt(2, 2, 6)
        grid.setAt(0, 3, 6)
        grid.setAt(3, 1, 6)

        assertEquals(3, grid.findAll(6).size)
    }

    @Test
    fun testInRange() {
        val grid = IntGrid(4, 4)

        assertTrue(grid.isInGrid(GridPoint(1, 1)))
        assertTrue(grid.isInGrid(GridPoint(0, 0)))
        assertTrue(grid.isInGrid(GridPoint(3, 3)))
        assertFalse(grid.isInGrid(GridPoint(4, 0)))
        assertFalse(grid.isInGrid(GridPoint(0, 4)))
        assertFalse(grid.isInGrid(GridPoint(-1, 0)))
        assertFalse(grid.isInGrid(GridPoint(0, -1)))

        assertFalse(grid.isNotInGrid(GridPoint(1, 1)))
        assertFalse(grid.isNotInGrid(GridPoint(0, 0)))
        assertFalse(grid.isNotInGrid(GridPoint(3, 3)))
        assertTrue(grid.isNotInGrid(GridPoint(4, 0)))
        assertTrue(grid.isNotInGrid(GridPoint(0, 4)))
        assertTrue(grid.isNotInGrid(GridPoint(-1, 0)))
        assertTrue(grid.isNotInGrid(GridPoint(0, -1)))
    }

    @Test
    fun testOperate() {
        val grid = IntGrid(2, 2)
        val pt = GridPoint(1, 1)

        assertEquals(0, grid.getAt(pt, -1))
        grid.operate(pt) { it + 1 }
        assertEquals(1, grid.getAt(pt, -1))
    }
}