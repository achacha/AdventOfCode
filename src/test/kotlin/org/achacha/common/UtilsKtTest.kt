package org.achacha.common

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
}
