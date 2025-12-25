package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2508Test {
    @Test
    fun part1sample() {
        assertEquals(40, Day2508().part1("/year2025/day2508_sample.txt", 10))
    }

    @Test
    fun part1() {
        assertEquals(40, Day2508().part1("/year2025/day2508.txt", 1000))
    }
}