package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2501Test {
    @Test
    fun rotatePart1() {
        val day2501 = Day2501()
        assertEquals(0, day2501.rotatePart1(0, -1, 0))
        assertEquals(1, day2501.rotatePart1(0, 1, 1))
        assertEquals(0, day2501.rotatePart1(99, 1, 1))
        assertEquals(99, day2501.rotatePart1(0, -1, 1))
        assertEquals(99, day2501.rotatePart1(50, -1, 251))
        assertEquals(1, day2501.rotatePart1(50, 1, 251))
    }

    @Test
    fun part1sample() {
        val resultDay2501Sample = Day2501().part1("/year2025/day2501_sample.txt")
        assertEquals(3, resultDay2501Sample)
    }

    @Test
    fun part1() {
        val resultDay2501 = Day2501().part1("/year2025/day2501.txt")
        assertEquals(1177, resultDay2501)
    }

    @Test
    fun rotatePart2() {
        val day2501 = Day2501()
        day2501.rotatePart2(0, -1, 0).let {
            assertEquals(0, it.first)
            assertEquals(0, it.second)
        }
        day2501.rotatePart2(1, -1, 2).let {
            assertEquals(99, it.first)
            assertEquals(1, it.second)
        }
        day2501.rotatePart2(50, 1, 251).let {
            assertEquals(1, it.first)
            assertEquals(3, it.second)
        }
        day2501.rotatePart2(50, -1, 251).let {
            assertEquals(99, it.first)
            assertEquals(3, it.second)
        }
        day2501.rotatePart2(0, 1, 100).let {
            assertEquals(0, it.first)
            assertEquals(1, it.second)
        }
        day2501.rotatePart2(99, 1, 102).let {
            assertEquals(1, it.first)
            assertEquals(2, it.second)
        }
        day2501.rotatePart2(50, 1, 1000).let {
            assertEquals(50, it.first)
            assertEquals(10, it.second)
        }
        day2501.rotatePart2(50, -1, 150).let {
            assertEquals(0, it.first)
            assertEquals(2, it.second)
        }
        day2501.rotatePart2(50, -1, 150).let {
            assertEquals(0, it.first)
            assertEquals(2, it.second)
        }
        day2501.rotatePart2(0, -1, 0).let {
            assertEquals(0, it.first)
            assertEquals(0, it.second)
        }
    }

    @Test
    fun rotatePart2_test() {
        val day2501 = Day2501()
        day2501.rotatePart2(0, 1, 60).let {
            assertEquals(60, it.first)
            assertEquals(0, it.second)
        }
        day2501.rotatePart2(40, -1, 40).let {
            assertEquals(0, it.first)
            assertEquals(1, it.second)
        }
    }

    @Test
    fun part2Partial() {
        val resultDay2501 = Day2501().part2("/year2025/day2501_partial.txt")
        assertEquals(9, resultDay2501)
    }


    @Test
    fun part2sample() {
        val resultDay2501Sample = Day2501().part2("/year2025/day2501_sample.txt")
        assertEquals(6, resultDay2501Sample)
    }

    /*
    !6861
    6768
     */
    @Test
    fun part2() {
        val resultDay2501 = Day2501().part2("/year2025/day2501.txt")
        assertEquals(6768, resultDay2501)
    }

}