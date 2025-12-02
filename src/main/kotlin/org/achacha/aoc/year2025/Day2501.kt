package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines
import kotlin.math.abs

class Day2501 {
    private val debug = true

    /**
     * Given `initial` rotate 100 click dial right(+1) or left(-1) based on `steps`
     *
     */
    fun rotatePart1(initial: Int, dir: Int, offset: Int): Int {
        // First get %100 since it's a circular dial
        return ((initial + dir * offset) % 100).let { if (it >= 0) it else it + 100 }
    }

    /**
     * Follow steps to rotate the 100-number dial, add 1 when dial points to 0
     */
    fun part1(resourcePathToData: String): Int {
        val steps = load2StringLines(resourcePathToData)
        var count = 0

        // Perform steps, when dial at 0 add to count
        var position = 50
        steps.forEach { line ->
            position = rotatePart1(position, if (line[0] == 'L') -1 else 1, line.substring(1).toInt())
            if (debug) {
                if (position == 0) println(
                    "+++Dial at 0 for line: $line  Position: $position"
                ) else println("Line: $line  Position: $position")
            }
            count += (if (position == 0) 1 else 0)
        }

        return count
    }

    /**
     * Count how many times the dial passes 0
     * @return Pair of position and times it passed zero
     */
    fun rotatePart2(initial: Int, dir: Int, offset: Int): Pair<Int,Int> {
        if (offset == 0) return Pair(initial, 0)

        // First get %100 since it's a circular dial
        val finalPosition = (initial + dir * offset)
        val returnPosition = (finalPosition % 100).let { if (it >= 0) it else it + 100 }   // same as from part1

        // Determine if passes 0
        val passesZero = (if (finalPosition < 0 && initial != 0) 1 else 0) + abs(finalPosition) / 100 + (if (finalPosition == 0 && initial != 0) 1 else 0)

        return Pair(returnPosition, passesZero)
    }

    fun part2(resourcePathToData: String): Int {
        val steps = load2StringLines(resourcePathToData)
        var count = 0

        var position = 50
        steps.forEach { line ->
            val dir = line[0]
            val offset = line.substring(1).toInt()
            val rawSum = if (dir == 'L') position - offset else position + offset
            if (debug) {
                print("Line: [$line] | InitialCount: $count  Position: [$position " + (if (dir == 'L') "-" else "+") + " $offset = ")
            }
            val result = rotatePart2(position, if (dir == 'L') -1 else 1, offset)
            position = result.first
            count += result.second
            if (debug) {
                println("$position] ($rawSum) count: [$count" + if (result.second > 0) " +${result.second}]" else "]")
            }
        }

        return count
    }

}