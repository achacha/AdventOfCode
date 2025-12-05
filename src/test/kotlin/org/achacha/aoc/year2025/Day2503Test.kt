package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2503Test {
    @Test
    fun testPart1sample() {
        val d = Day2503()
        assertEquals(357, d.part1("/year2025/day2503_sample.txt"))
    }

    @Test
    fun testPart1() {
        val d = Day2503()
        assertEquals(17155, d.part1("/year2025/day2503.txt"))
    }
}