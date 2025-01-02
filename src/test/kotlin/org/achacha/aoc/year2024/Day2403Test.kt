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
    fun `validate input converter 1`() {
        val day = Day2403()
        assertEquals(
            "1234",
            day.extractValidCode("1234")
        )

        assertEquals(
            "",
            day.extractValidCode("don't()1234")
        )

        assertEquals(
            "1234",
            day.extractValidCode("12do()3do()4do()")
        )

        assertEquals(
            "3",
            day.extractValidCode("don't()12do()3don't()4do()")
        )

        assertEquals(
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))",
            day.extractValidCode("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
        )

        assertEquals(
            "xmul(2,4)&mul[3,7]!^?mul(8,5))",
            day.extractValidCode("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
        )
    }

    @Test
    fun `part2 sample 1`() {
        assertEquals(161, Day2403().part2("/year2024/day03_sample.txt"))
    }

    @Test
    fun `part2 sample 2`() {
        assertEquals(48, Day2403().part2("/year2024/day03_sample2.txt"))
    }

    @Test
    fun `part 2`() {
        assertEquals(62098619, Day2403().part2("/year2024/day03.txt"))
    }
}