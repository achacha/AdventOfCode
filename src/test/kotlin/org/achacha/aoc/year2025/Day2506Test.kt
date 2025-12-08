package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day2506Test {
    @Test
    fun parseInputData1() {
        val inputData = Day2506().parseInputData1("/year2025/day2506_sample.data")
        assertEquals(3, inputData.rows.size)
        assertEquals(4, inputData.rows[0].size)
        assertEquals(4, inputData.rows[1].size)
        assertEquals(4, inputData.rows[2].size)
        assertEquals(4, inputData.opera.size)
    }

    @Test
    fun part1sample() {
        assertEquals(4277556, Day2506().part1("/year2025/day2506_sample.data"))
    }

    @Test
    fun part1() {
        assertEquals(6172481852142, Day2506().part1("/year2025/day2506.data"))
    }

    @Test
    fun part2sample() {
        assertEquals(BigInteger("3263827"), Day2506().part2("/year2025/day2506_sample.data"))
    }

    @Test
    fun part2() {
        assertEquals(BigInteger("10188206723429"), Day2506().part2("/year2025/day2506.data"))
    }
}
