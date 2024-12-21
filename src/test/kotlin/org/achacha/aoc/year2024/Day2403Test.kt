package org.achacha.aoc.year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2403Test {
    @Test
    fun `part1 sample`() {
        assertEquals(161, Day2403().part1("/year2024/day03_sample.txt"))
    }

    @Test
    fun `part 1`() {
        assertEquals(183788984, Day2403().part1("/year2024/day03.txt"))
    }

    @Test
    fun `part2 sample`() {
        assertEquals(4, Day2403().part2("/year2024/day03_sample.txt"))
    }

    @Test
    fun `part 2`() {
        assertEquals(621, Day2403().part2("/year2024/day03.txt"))
    }
}