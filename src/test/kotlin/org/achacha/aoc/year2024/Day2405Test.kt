package org.achacha.aoc.year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2405Test {
    @Test
    fun `verify data part 1 sample fail`() {
        val day = Day2405("/year2024/day05_sample_fail.txt")
        assertEquals(21, day.precedencePairs.size)
        assertEquals(3, day.updateLists.size)

        assertEquals(0, day.part1())
    }

    @Test
    fun `verify data part 1 sample`() {
        val day = Day2405("/year2024/day05_sample.txt")
        assertEquals(21, day.precedencePairs.size)
        assertEquals(6, day.updateLists.size)

        assertEquals(143, day.part1())
    }

    @Test
    fun `do part1`() {
        val day = Day2405("/year2024/day05.txt")
        assertEquals(4135, day.part1())
    }
}