package org.achacha.aoc.year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day2502Test {
    @Test
    fun testParseInputPart1() {
        val idParts = Day2502().parseInputPart1("/year2025/day2502_sample.txt")
        assertEquals(11, idParts.size)
    }

    @Test
    fun testContainsRepeating() {
        val d = Day2502()

        assertEquals("1212", d.containsRepeating("1212"))
        assertEquals("11", d.containsRepeating("11"))
        assertEquals("123456123456", d.containsRepeating("123456123456"))

        assertNull(d.containsRepeating("0101"))
        assertNull(d.containsRepeating("12"))
        assertNull(d.containsRepeating("12345"))
        assertNull(d.containsRepeating("123454321"))
    }

    @Test
    fun testContainsRepeatingWithOffset() {
        val d = Day2502()

        assertEquals("1212", d.containsRepeating("1212"))
        assertEquals("11", d.containsRepeating("11"))
        assertEquals("1010", d.containsRepeating("1010"))
        assertEquals("5656", d.containsRepeating("12345656789"))

        assertNull(d.containsRepeating("0101"))
        assertNull(d.containsRepeating("12012"))
        assertNull(d.containsRepeating("12013"))
        assertNull(d.containsRepeating("1"))
        assertNull(d.containsRepeating("12"))
        assertNull(d.containsRepeating("12314"))
    }

    @Test
    fun part1sample() {
        val resultDay2502Sample = Day2502().part1("/year2025/day2502_sample.txt")
        assertEquals(1227775554, resultDay2502Sample)
    }

    @Test
    fun part1() {
        val resultDay2502 = Day2502().part1("/year2025/day2502.txt")
        assertEquals(40214376723, resultDay2502)
    }

    @Test
    fun testContainsRepeatingSequential() {
        val d = Day2502()
        assertEquals("1212", d.containsRepeatingSequential("1212"))
        assertEquals("1234512345", d.containsRepeatingSequential("1234512345"))
        assertEquals("123123123", d.containsRepeatingSequential("123123123"))
        assertEquals("828282", d.containsRepeatingSequential("828282"))
        assertEquals("111", d.containsRepeatingSequential("111"))

        assertNull(d.containsRepeatingSequential("110"))
        assertNull(d.containsRepeatingSequential("12312312"))
        assertNull(d.containsRepeatingSequential("121312"))
        assertNull(d.containsRepeatingSequential("1021012"))
    }

    @Test
    fun part2sample() {
        val resultDay2502Sample = Day2502().part2("/year2025/day2502_sample.txt")
        assertEquals(BigInteger("4174379265"), resultDay2502Sample)
    }

    @Test
    fun part2() {
        val resultDay2502 = Day2502().part2("/year2025/day2502.txt")
        assertEquals(BigInteger("50793864718"), resultDay2502)
    }
}