package org.achacha.aoc.year2024

import org.achacha.common.getAt
import org.achacha.common.load2StringLines

class Day2404 {
    fun part1(resourcePath: String): Int {
        var count = 0
        val lines = load2StringLines(resourcePath)
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == 'X') {
                    count += checkE(lines, x, y)
                    count += checkW(lines, x, y)
                    count += checkN(lines, x, y)
                    count += checkS(lines, x, y)
                    count += checkNE(lines, x, y)
                    count += checkNW(lines, x, y)
                    count += checkSE(lines, x, y)
                    count += checkSW(lines, x, y)
                }
            }
        }
        return count
    }

    fun checkE(lines: List<String>, x: Int, y: Int): Int = if (
            lines.getAt(x + 1, y) == 'M'
            && lines.getAt(x + 2, y) == 'A'
            && lines.getAt(x + 3, y) == 'S'
        ) 1 else 0

    fun checkW(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x - 1, y) == 'M'
        && lines.getAt(x - 2, y) == 'A'
        && lines.getAt(x - 3, y) == 'S'
    ) 1 else 0

    fun checkN(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x, y - 1) == 'M'
        && lines.getAt(x, y - 2) == 'A'
        && lines.getAt(x, y - 3) == 'S'
    ) 1 else 0

    fun checkS(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x, y + 1) == 'M'
        && lines.getAt(x, y + 2) == 'A'
        && lines.getAt(x, y + 3) == 'S'
    ) 1 else 0

    fun checkNW(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x - 1, y - 1) == 'M'
        && lines.getAt(x - 2, y - 2) == 'A'
        && lines.getAt(x - 3, y - 3) == 'S'
    ) 1 else 0

    fun checkNE(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x + 1, y - 1) == 'M'
        && lines.getAt(x + 2, y - 2) == 'A'
        && lines.getAt(x + 3, y - 3) == 'S'
    ) 1 else 0

    fun checkSW(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x - 1, y + 1) == 'M'
        && lines.getAt(x - 2, y + 2) == 'A'
        && lines.getAt(x - 3, y + 3) == 'S'
    ) 1 else 0

    fun checkSE(lines: List<String>, x: Int, y: Int): Int = if (
        lines.getAt(x + 1, y + 1) == 'M'
        && lines.getAt(x + 2, y + 2) == 'A'
        && lines.getAt(x + 3, y + 3) == 'S'
    ) 1 else 0

    fun part2(resourcePath: String): Int {
        var count = 0
        val lines = load2StringLines(resourcePath)
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == 'A') {
                    count += checkX(lines, x, y)
                }
            }
        }
        return count
    }

    /**
     * M S  M M  S S  S M
     *  A    A    A    A
     * M S  S S  M M  S M
     */
    fun checkX(lines: List<String>, x: Int, y: Int): Int {
        if (
            lines.getAt(x - 1, y - 1) == 'M'
            && lines.getAt(x - 1, y + 1) == 'M'
            && lines.getAt(x + 1, y - 1) == 'S'
            && lines.getAt(x + 1, y + 1) == 'S'
        ) return 1

        if (
            lines.getAt(x - 1, y - 1) == 'M'
            && lines.getAt(x - 1, y + 1) == 'S'
            && lines.getAt(x + 1, y - 1) == 'M'
            && lines.getAt(x + 1, y + 1) == 'S'
        ) return 1

        if (
            lines.getAt(x - 1, y - 1) == 'S'
            && lines.getAt(x - 1, y + 1) == 'M'
            && lines.getAt(x + 1, y - 1) == 'S'
            && lines.getAt(x + 1, y + 1) == 'M'
        ) return 1

        if (
            lines.getAt(x - 1, y - 1) == 'S'
            && lines.getAt(x - 1, y + 1) == 'S'
            && lines.getAt(x + 1, y - 1) == 'M'
            && lines.getAt(x + 1, y + 1) == 'M'
        ) return 1

        return 0
    }
}