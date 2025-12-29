package org.achacha.aoc.year2025

import org.achacha.aoc.year2025.Day2508.JunctionSet
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2508Test {
    @Test
    fun testJunctionSetCompare() {
        val sets = HashSet<JunctionSet>()
        sets.add(JunctionSet(mutableSetOf(1, 2, 3, 4)))
        val tobeRemoved = JunctionSet(mutableSetOf())
        sets.add(tobeRemoved)
        sets.add(JunctionSet(mutableSetOf(5, 6)))

        sets.forEach {
            assertTrue(sets.contains(it))
        }
        assertFalse(sets.contains(JunctionSet(mutableSetOf(9, 0))))

        assertTrue(sets.remove(tobeRemoved))
        assertEquals(2, sets.size)
    }

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

    @Test
    fun part2_case1() {
        assertEquals(12, Day2508(true).part2("/year2025/day2508_testcase_01.txt"))
    }

    @Test
    fun part2sample() {
        assertEquals(25272, Day2508(true).part2("/year2025/day2508_sample.txt"))
    }

    @Test
    fun part2() {
        // 8699593420 too high
        assertEquals(8520040659, Day2508().part2("/year2025/day2508.txt"))
    }
}