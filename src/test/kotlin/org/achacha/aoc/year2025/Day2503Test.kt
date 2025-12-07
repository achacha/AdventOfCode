package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

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

    @Test
    fun testPart2sample() {
        val d = Day2503()
        assertEquals(BigInteger("3121910778619"), d.part2("/year2025/day2503_sample.txt"))
    }

    @Test
    fun testPart2test() {
        val d = Day2503()
        assertEquals(BigInteger("564484446343"), d.part2("/year2025/day2503_test.txt"))
    }

    @Test
    fun findHighest() {
        val d = Day2503()
        assertEquals(BigInteger("987654321111"), d.findHighest("987654321111111"))
        assertEquals(BigInteger("811111111119"), d.findHighest("811111111111119"))
        assertEquals(BigInteger("434234234278"), d.findHighest("234234234234278"))
        assertEquals(BigInteger("888911112111"), d.findHighest("818181911112111"))
    }

    @Test
    fun testPart2() {
        val d = Day2503()
        assertEquals(BigInteger("144427020092230"), d.part2("/year2025/day2503.txt"))
    }
}