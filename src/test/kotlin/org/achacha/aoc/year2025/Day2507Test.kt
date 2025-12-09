package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2507Test {
    @Test
    fun part1sample() {
        val d = Day2507()
        assertEquals(21, Day2507().part1("/year2025/day2507_sample.txt"))
    }

    @Test
    fun part1() {
        val d = Day2507()
        assertEquals(1630, Day2507().part1("/year2025/day2507.txt"))
    }

}