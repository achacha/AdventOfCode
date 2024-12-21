package org.achacha.aoc.year2024

import org.achacha.common.asString
import org.achacha.common.load2column_Int
import org.achacha.common.removeOne
import kotlin.math.abs

/**
 * https://adventofcode.com/2024/day/2
 */
class Day2402 {
    private val debug = true

    /**
     * @return how many rows are safe
     */
    fun part1(resourcePath: String): Int {
        val data = load2column_Int(resourcePath)

        return data.sumOf { line ->
            checkLine(line)
        }
    }

    private fun checkLine(line: Array<Int>): Int {
        var ret = 1
        val dir = if (line[1] > line[0]) 1 else -1
        for (i in 1..<line.size) {
            val diff = line[i] - line[i - 1]
            if (diff == 0 || diff > 0 && dir < 0 || diff < 0 && dir > 0 || abs(diff) < 1 || abs(diff) > 3) {
                ret = 0
                break
            }
        }
        return ret
    }

    /**
     * @return how many rows are safe
     */
    fun part2(resourcePath: String): Int {
        val data = load2column_Int(resourcePath)

        return data.sumOf { line ->
            var ret = 0
            if (checkLine(line) == 1) {
                ret = 1
            } else {
                // Remove 1 until it passes
                for (i in 0..line.size) {
                    val lineMinusOne = removeOne(line, i)
                    if (checkLine(lineMinusOne) == 1) {
                        ret = 1
                        break
                    }
                }
            }
            ret
        }
    }

}