package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day2505Test {
    @Test
    fun parseInputData() {
        val d = Day2505()
        val inputData = d.parseInputData("/year2025/day2505_sample.txt")

        assertEquals(4, inputData.fresh.size)
        assertEquals(Pair(3,5), inputData.fresh[0])
        assertEquals(6, inputData.produce.size)
        assertTrue(inputData.produce.contains(1))
        assertTrue(inputData.produce.contains(32))
    }

    @Test
    fun part2sample() {
        assertEquals(3, Day2505().part1("/year2025/day2505_sample.txt"))
    }

    @Test
    fun part2() {
        assertEquals(690, Day2505().part1("/year2025/day2505.txt"))
    }

}