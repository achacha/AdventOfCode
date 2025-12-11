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
        assertEquals(1630, Day2507().part1("/year2025/day2507.txt"))
    }

    @Test
    fun part2testSmall() {
        assertEquals(2, Day2507().part2("/year2025/day2507_test_small.txt"))
    }

    @Test
    fun part2testMediumCache() {
        assertEquals(6, Day2507().part2("/year2025/day2507_test_medium_cache.txt"))
    }

    @Test
    fun part2testMedium() {
        assertEquals(6, Day2507().part2("/year2025/day2507_test_medium.txt"))
    }

    @Test
    fun part2test() {
        assertEquals(4, Day2507().part2("/year2025/day2507_test.txt"))
    }

    @Test
    fun part2sample() {
        assertEquals(40, Day2507().part2("/year2025/day2507_sample.txt"))
    }

    @Test
    fun part2() {
        assertEquals(1630, Day2507().part2("/year2025/day2507.txt"))
    }
}