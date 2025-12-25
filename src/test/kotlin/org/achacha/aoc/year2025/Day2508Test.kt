package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2508Test {
    @Test
    fun part1sample() {
        assertEquals(40, Day2508().part1("/year2025/day2508_sample.txt", 10))
    }

    @Test
    fun part1_case1() {
        assertEquals(12, Day2508(true).part1("/year2025/day2508_testcase_01.txt", 5))
    }

    @Test
    fun part1() {
        // 89100 too high
        // 57970 is correct
        assertEquals(57970, Day2508().part1("/year2025/day2508.txt", 1000))
    }
}