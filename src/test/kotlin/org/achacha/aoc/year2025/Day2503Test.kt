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
        assertEquals("987654321111", d.findLargestMonotonicStack("987654321111111"))
        assertEquals("811111111119", d.findLargestMonotonicStack("811111111111119"))
        assertEquals("434234234278", d.findLargestMonotonicStack("234234234234278"))
        assertEquals("888911112111", d.findLargestMonotonicStack("818181911112111"))
        assertEquals("654444724422", d.findLargestMonotonicStack("2261232211332221132222312231213241233432222222242422212425432333414123222221232322223134222233724422"))
    }

    @Test
    fun testPart2() {
        val d = Day2503()
        assertEquals(BigInteger("169685670469164"), d.part2("/year2025/day2503.txt"))
    }
}