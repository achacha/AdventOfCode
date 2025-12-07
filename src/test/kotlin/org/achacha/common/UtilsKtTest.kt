package org.achacha.common

import org.achacha.aoc.year2025.Day2502
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtilsKtTest {
    @Test
    fun load2columns() {
        val data0 = load2column("/unittest/load2column_1.txt", " ")
        assertEquals(4, data0.size)
        assertEquals("0", data0[0][0])
        assertEquals("1", data0[0][1])
        assertEquals("2", data0[1][0])
        assertEquals("3", data0[1][1])
        assertEquals("4", data0[2][0])
        assertEquals("5", data0[2][1])
        assertEquals("6", data0[3][0])
        assertEquals("7", data0[3][1])
    }

    @Test
    fun load2columns_Int() {
        val data0 = load2column_Int("/unittest/load2column_1.txt", " ")
        assertEquals(4, data0.size)
        assertEquals(0, data0[0][0])
        assertEquals(1, data0[0][1])
        assertEquals(2, data0[1][0])
        assertEquals(3, data0[1][1])
        assertEquals(4, data0[2][0])
        assertEquals(5, data0[2][1])
        assertEquals(6, data0[3][0])
        assertEquals(7, data0[3][1])
    }

    @Test
    fun intArrayAsString() {
        val data0 = load2column_Int("/unittest/load2column_1.txt", " ")
        println(data0.asString())
    }

    @Test
    fun testRemoveOne() {
        assertArrayEquals(arrayOf(1,2,3,4), removeOne(arrayOf(0,1,2,3,4), 0))
        assertArrayEquals(arrayOf(0,1,2,3), removeOne(arrayOf(0,1,2,3,4), 4))
        assertArrayEquals(arrayOf(0,1,3,4), removeOne(arrayOf(0,1,2,3,4), 2))
    }

    @Test
    fun testIncrementer() {
        assertEquals("00002", stringNumberIncrementer("00001"))
        assertEquals("99999", stringNumberIncrementer("99998"))
        assertEquals("6", stringNumberIncrementer("5"))
        assertEquals("100", stringNumberIncrementer("99"))
        assertEquals("99999", stringNumberIncrementer("99998"))
        assertEquals("20", stringNumberIncrementer("19"))
    }

    @Test
    fun testLoadCharGrid() {
        val grid = loadCharGrid("/unittest/chargrid_1.txt")
        assertEquals(4, grid.size)
        assertEquals(4, grid[0].size)
        assertEquals('A', grid[0][0])
        assertEquals('B', grid[0][1])
        assertEquals('D', grid[0][3])
        assertEquals('F', grid[1][1])
        assertEquals('K', grid[2][2])
        assertEquals('P', grid[3][3])
    }
}
